package co.tton.qcloud.system.service;

import co.tton.qcloud.system.domain.HomePageModel;

import java.text.ParseException;

public interface IHomePageService {
    HomePageModel getHomePage(String regionId);
}
