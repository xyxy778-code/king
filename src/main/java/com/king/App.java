package com.king;

import com.king.controller.MenuController;

/**
 * 王者荣耀管理系统 - 程序入口
 * 启动控制台菜单交互界面
 */
public class App {
    public static void main(String[] args) {
        new MenuController().start();
    }
}
