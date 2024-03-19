**Veteriner Yönetim Sistemi**
---
---

Bu proje;  
Veteriner doktorlarının bilgilerini, doktorların müsait günlerini (tarih olarak), müşterileri ve onlara ait hayvanları, hayvanlara uygulanan bir aşıları (tarihiyle birlikte) kaydetme ve yönetme işlemleriyle tarih ve saate göre müşteri için randevu oluşturma işlemini amaçlayan bir API'dir.

Katmanlı mimari yapısı kullanılarak aşağıdaki teknolojilerden faydalanılmıştır:  
- Spring Boot,  
- Spring Web,   
- Spring Boot Dev Tools,  
- MySql,   
- Spring Data Jpa,   
- Lombok,  
- ModelMapper,  
- Validation,  
- Postman  

Projede Oluşturulan Entityler:  
---
---
- Animal  
- Customer  
- Doctor  
- Vaccine
- AvailableDate  
- Appointment

Proje Nasıl Çalıştırılır?
---  
-----
1. Projenin kaynak kodları indirilir.  
2. Mysql veritabanı oluşturulur.(Proje dosyasında ilgili sql kodları bulunmaktadır.)  
3. Proje herhangi bir Java IDE'sinde açılır.  
4. Veritabanı bağlantı bilgileri uygulamadaki application.properties dosyasında güncellenir ve proje çalıştırılır.  
5. http://localhost:8080 adresinden ilgili API'ye erişilir.  


Projede Yapılan Temel İşlemler:
---  
-------

Hayvanların ve Sahiplerinin (customer) Yönetimi:  

Hayvanları kaydetme, bilgilerini güncelleme, görüntüleme ve silme, hayvan sahiplerini kaydetme, bilgilerini güncelleme, görüntüleme ve silme (CRUD işlemleri).  
Hayvan sahipleri isme göre filtrelenecek şekilde ve hayvan sahibinin sistemde kayıtlı tüm hayvanlarını görüntülemek için API end point'inin oluşturulması,  
Hayvan sahibine göre filtrelenmesi.

Uygulanan Aşıların Yönetimi:  

Hayvanlara uygulanan aşıları kaydetme, bilgilerini güncelleme, görüntüleme ve silme işlemleri(CRUD işlemleri).
Eğer hastaya ait aynı tip aşının  aşı koruyuculuk bitiş tarihi daha gelmemiş ise sisteme yeni aşı girişinin engellenmesi,
Hayvan id’sine göre belirli bir hayvana ait tüm aşı kayıtlarını listelemek için gerekli end pointin oluşturulması,
Kullanıcının aşı koruyuculuk bitiş tarihi yaklaşan hayvanları listeleyebilmesi için girilen başlangıç ve bitiş tarihlerine göre aşı koruyuculuk tarihi bu aralıkta olan hayvanların listesini geri döndüren API end  point'inin oluşturulması.

Randevu Yönetimi:  

Hayvanların aşı ve muayene randevularının oluşturulması, bilgilerinin güncellenmesi, görüntülenmesi ve silinmesi(CRUD işlemleri).  
Randevular sistemde tarih ve saat şeklinde tutulması.  
Doktorların uygun olduğu  tarih ve saatlerde randevu oluşturulması. (Randevular saat başı olmaktadır.)  
Randevu kaydı oluştururken doktorun girilen tarihte müsait günü olup olmadığı eğer ki müsait günü varsa randevu kayıtlarında girilen saatte başka bir randevusu olup olmadığının kontrolü.  
Randevuların girilen tarih-doktor ve tarih-hayvan olarak filtrelenmesi.  

Veteriner Doktor Yönetimi:  

Veteriner doktorların kaydedilmesi, bilgilerinin güncellenmesi, görüntülenmesi ve silinmesi(CRUD işlemleri).  
Doktorların müsait günlerinin yönetimi.  
Doktorların müsait günlerini ekleme, bilgilerini güncelleme, görüntüleme ve silme(CRUD işlemleri).

EndPoints:  
---
-----
API'ye ait ilgili endpointler aşağıda listelenmiştir.  

1- Animal:
---
| HTTP METODU | ENDPOINT                          | AÇIKLAMASI                                            |
|-------------|:----------------------------------|:------------------------------------------------------|
| GET         | /v1/animal                        | Kayıtlı tüm hayvanları listeler.                      |
| GET         | /v1/animal/{id}                   | ID'ye göre hayvanları listeler.                       |
| GET         | /v1/animal?name={name}            | İsme göre kayıtlı hayvanları listeler.                |
| GET         | /v1/animal/customer/{customer id} | Müşteri ID'ye göre müşteriye ait hayvanları listeler. |
| POST        | /v1/animal                        | Yeni hayvan ekler.                                    |
| PUT         | /v1/animal/{id}                   | ID'ye göre hayvanları günceller.                      |
| DELETE      | /v1/animal/{id}                   | ID'ye göre hayvanları siler.                          |

2- Customer:
---
| HTTP METODU | ENDPOINT                           | AÇIKLAMASI                                     |
|-------------|:-----------------------------------|:-----------------------------------------------|
| GET         | /v1/customer                       | Kayıtlı tüm hayvan sahiplerini listeler.       |
| GET         | /v1/customer/{id}                  | ID'ye göre hayvan sahiplerini listeler.        |
| GET         | /v1/customer?name={name}           | İsme göre kayıtlı hayvan sahiplerini listeler. |
| POST        | /v1/customer                       | Yeni hayvan sahibi ekler.                      |
| PUT         | /v1/customer/{id}                  | ID'ye göre hayvan sahiplerini günceller.       |
| DELETE      | /v1/customer/{id}                  | ID'ye göre hayvan sahiplerini siler.           |

3- Doctor:
---
| HTTP METODU | ENDPOINT        | AÇIKLAMASI                       |
|-------------|:----------------|:---------------------------------|
| GET         | /v1/doctor      | Kayıtlı tüm doktorları listeler. |
| GET         | /v1/doctor/{id} | ID'ye göre doktorları listeler.  |
| POST        | /v1/doctor      | Yeni doktor ekler.               |
| PUT         | /v1/doctor/{id} | ID'ye göre doktorları günceller. |
| DELETE      | /v1/doctor/{id} | ID'ye göre doktorları siler.     |

4- Vaccine:
---
| HTTP METODU | ENDPOINT                           | AÇIKLAMASI                                                                        |
|-------------|:-----------------------------------|:----------------------------------------------------------------------------------|
| GET         | /v1/vaccine                        | Kayıtlı tüm aşıları listeler.                                                     |
| GET         | /v1/vaccine/{id}                   | ID'ye göre aşıları listeler.                                                      |
| GET         | /v1/vaccine/animal/{animal id}     | Hayvan ID'ye göre kayıtlı aşılarılisteler.                                        |
| GET         | /vaccine/animal/vaccinesWillExpire | Girilen tarihlere göre aşı koruyuculuk bitiş tarihi yaklaşan hayvanları listeler. |
| POST        | /v1/vaccine                        | Yeni aşı ekler.                                                                   |
| PUT         | /v1/vaccine/{id}                   | ID'ye göre aşıları günceller.                                                     |
| DELETE      | /v1/vaccine/{id}                   | ID'ye göre aşıları siler.                                                         |

5- AvailableDate:
---
| HTTP METODU | ENDPOINT                 | AÇIKLAMASI                                          |
|-------------|:-------------------------|:----------------------------------------------------|
| GET         | /v1/availableDate        | Kayıtlı doktorların çalıştığı günleri listeler.     |
| GET         | /v1/availableDate/{id}   | ID'ye göre doktorların çalıştığı günleri listeler.  |
| POST        | /v1/availableDate        | Yeni çalışma günü ekler.                            |
| PUT         | /v1/availableDate/{id}   | ID'ye göre doktorların çalıştığı günleri günceller. |
| DELETE      | /v1/availableDate/{id}   | ID'ye göre doktorların çalıştığı günleri siler.     |

6- Appointment:
---

| HTTP METODU | ENDPOINT                                                                                       | AÇIKLAMASI                                                                       |
|-------------|:-----------------------------------------------------------------------------------------------|:---------------------------------------------------------------------------------|
| GET         | /v1/appointment                                                                                | Kayıtlı randevuları listeler.                                                    |
| GET         | /v1/appointment/{id}                                                                           | ID'ye göre randevuları listeler.                                                 |
| GET         | /v1/appointment/filter/doctor?doctorId={id}&startDate={yyyy-mm-dd}&endDate={yyyy-mm-dd}        | Doktor ID, Başlangıç Tarihi ve Bitiş Tarihine göre kayıtlı randevuları listeler. |
| GET         | /v1/appointment/filter/animal?animalId={id}&startDate={yyyy-mm-dd}&endDate={yyyy-mm-dd}        | Hayvan ID, Başlangıç Tarihi ve Bitiş Tarihine göre kayıtlı randevuları listeler. |
| POST        | /v1/appointment                                                                                | Yeni randevu ekler.                                                              |
| PUT         | /v1/appointment/{id}                                                                           | ID'ye göre randevuları günceller.                                                |
| DELETE      | /v1/appointment/{id}                                                                           | ID'ye göre randevuları siler.                                                    |
