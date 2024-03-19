package dev.tugbaislyn.core.utilies;

public class Msg { //Hazır mesajların olduğu sınıf
    public static final String CREATED="Kayıt Eklendi!"; // Sonradan bu mesaj değiştirlmek istenirse tek tek uğraşmak yerine gelip buradan tek seferde değiştirebiliriz.
    public static final String OK="İşlem Başarılı!!";
    public static final String VALIDATE_ERROR="Veri Doğrulama Hatası!";
    public static final String NOT_FOUND=" numaralı id sistemde kayıtlı değildir. Lütfen gerekli kontrolleri yapıp tekrar deneyiniz!!";
    public static final String CONFLICT="Aynı bilgiler tekrar kaydedilemez!";
    public static final String CONFLICT_APPOINTMENT= "Doktorun bu tarihte başka bir randevusu bulunmaktadır.";
    public static final String NOT_FOUND_APPOINTMENT="Girilen tarihlerde uygun randevu bulunamamıştır!";
    public static final String CONFLICT_VACCINE="Bu aşının koruma süresi henüz bitmemiştir! Süresi bitmeyen aşı tekrar kayıt edilemez!";
    public static final String WARNING_VACCINE="Bu aşının koruma başlangıç tarihi,önceki aşısının süresi bittikten sonra başlamalıdır";
    public static final String WARNING_VACCINE2="Aşının başlangıç tarihi bitiş tarihinden önce olmalıdır!";
    public static final String CONFLICT_UNPROCESSABLE= "Doktor bu tarihte çalışmamaktadır!";
    public static final String CONFLICT_APPOINTMENTWANIMAL= "Girilen hayvanın bu tarihte başka bir randevusu bulunmaktadır!";
}
