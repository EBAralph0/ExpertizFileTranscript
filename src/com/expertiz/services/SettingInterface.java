package com.expertiz.services;

import java.util.List;

import javax.ejb.Remote;

import com.expertiz.entities.Setting;


@Remote
public interface SettingInterface {
	
	public Setting create(Setting s);
    public Setting update(Setting s);
    public void remove(int id);
    public Setting getSetting(int id);
    public List<Setting> getAllSetting();
	public long count();
	
}
