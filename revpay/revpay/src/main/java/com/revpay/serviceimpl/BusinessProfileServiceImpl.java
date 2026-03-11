package com.revpay.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revpay.dto.BusinessProfileDTO;
import com.revpay.entity.BusinessProfile;
import com.revpay.entity.User;
import com.revpay.repository.BusinessProfileRepository;
import com.revpay.repository.UserRepository;
import com.revpay.service.BusinessProfileService;

@Service
public class BusinessProfileServiceImpl implements BusinessProfileService {

    @Autowired
    private BusinessProfileRepository businessProfileRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void createProfile(BusinessProfileDTO dto) {

        BusinessProfile profile = new BusinessProfile();

        profile.setBusinessName(dto.getBusinessName());
        profile.setGstNumber(dto.getGstNumber());
        profile.setBusinessType(dto.getBusinessType());
        profile.setBusinessAddress(dto.getBusinessAddress());

        User user = userRepository.findById(dto.getUserId()).orElse(null);

        if(user != null){
            profile.setUser(user);
        }

        businessProfileRepository.save(profile);
    }

    @Override
    public BusinessProfileDTO getProfileByUser(Long userId) {

        BusinessProfile profile =
        businessProfileRepository.findByUserId(userId);

        if(profile == null){
            return null;
        }

        BusinessProfileDTO dto = new BusinessProfileDTO();

        dto.setId(profile.getId());
        dto.setBusinessName(profile.getBusinessName());
        dto.setGstNumber(profile.getGstNumber());
        dto.setBusinessType(profile.getBusinessType());
        dto.setBusinessAddress(profile.getBusinessAddress());
        dto.setUserId(profile.getUser().getId());

        return dto;
    }
}