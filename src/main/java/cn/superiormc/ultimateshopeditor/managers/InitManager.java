package cn.superiormc.ultimateshopeditor.managers;

import cn.superiormc.ultimateshop.utils.CommonUtil;
import cn.superiormc.ultimateshopeditor.UltimateShopEditor;

import java.io.File;

public class InitManager {
    public static InitManager initManager;

    private boolean firstLoad = false;

    public InitManager() {
        initManager = this;
        File file = new File(UltimateShopEditor.instance.getDataFolder(), "message.yml");
        if (!file.exists()) {
            firstLoad = true;
        }
        init();
    }

    public void init() {
        resourceOutput("message.yml", true);
    }

    private void resourceOutput(String fileName, boolean fix) {
        File tempVal1 = new File(UltimateShopEditor.instance.getDataFolder(), fileName);
        if (!tempVal1.exists()) {
            if (!firstLoad && !fix) {
                return;
            }
            File tempVal2 = new File(fileName);
            if (tempVal2.getParentFile() != null && fix) {
                CommonUtil.mkDir(tempVal2.getParentFile());
            }
            UltimateShopEditor.instance.saveResource(tempVal2.getPath(), false);
        }
    }
}
