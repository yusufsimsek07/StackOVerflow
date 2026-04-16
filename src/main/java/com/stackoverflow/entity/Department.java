package com.stackoverflow.entity;

public enum Department {
    YAZILIM("Yazılım Geliştirme", "bg-primary"),
    İNSAN_KAYNAKLARI("İnsan Kaynakları", "bg-pink"),
    SATIŞ("Satış & Pazarlama", "bg-success"),
    TASARIM("UI/UX Tasarım", "bg-warning text-dark"),
    MUHASEBE("Muhasebe & Finans", "bg-secondary"),
    YÖNETİM("Yönetim", "bg-danger"),
    SİSTEM_AĞ("Sistem & Ağ", "bg-info text-dark");

    private final String label;
    private final String badgeClass;

    Department(String label, String badgeClass) {
        this.label = label;
        this.badgeClass = badgeClass;
    }

    public String getLabel() { return label; }
    public String getBadgeClass() { return badgeClass; }
}
