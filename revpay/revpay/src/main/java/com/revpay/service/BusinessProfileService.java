package com.revpay.service;

import com.revpay.dto.BusinessProfileDTO;

public interface BusinessProfileService {

    void createProfile(BusinessProfileDTO dto);

    BusinessProfileDTO getProfileByUser(Long userId);

}