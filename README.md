<div align="center">
  <h1>🚀 Kurumsal Soru-Cevap & Bilgi Paylaşım Platformu</h1>
  <p><strong>Jakarta EE 11 ve Java tabanlı, StackOverflow mimarisinden ilham alınarak geliştirilmiş modern topluluk platformu.</strong></p>
</div>

<hr/>

## 📖 Proje Hakkında
Bu proje, kurum içi veya genel departmanların (,Yazılım Geliştirme, İnsan Kaynakları, Tasarım vb.) birbirleriyle etkileşime girmesi, soru sorması ve teknik problemleri kolektif bir yardımlaşma ile çözmesi için geliştirilmiş **Jakarta EE** tabanlı bir Web Uygulamasıdır. Modern mimari standartları ve güncel Java ekosistemine uygun olarak sıfırdan tasarlanmıştır.

## ✨ Temel Özellikler
* **Rol ve Departman Sistemi:** Kullanıcıların uzmanlık alanlarına ve departmanlarına göre sınıflandırılması.
* **Akıllı Sorular & Kategori Filtreleme:** Soruların doğrudan ilgili departman adı altında sorulabilmesi ve izlenebilmesi.
* **Modern Arayüz (Glassmorphism):** Saf CSS ve Bootstrap 5 kullanarak zenginleştirilmiş, kullanıcı dostu "Buzlu Cam" (Glassmorphism) arayüz deneyimi.
* **Profil ve Medya Yönetimi:** Kullanıcıların kendi profillerine cihazlarından fotoğraf (avatar) yükleyebilmesi. Yüklemeyenler için yapay zeka destekli otomatik isim baş harfi avatarı oluşturulması.
* **Etkileşim (Oylama & Skor):** Sorulara ait anlık skorlama (Upvote/Downvote) işlemleri.

## 🛠️ Kullanılan Teknolojiler
Bu proje oluşturulurken hiçbir dış (kısıtlanmış) Framework kullanılmamış, standart Jakarta EE önergelerine uyulmuştur:

* **Çekirdek:** Java 21/25, Jakarta EE 11 
* **Sunucu (Application Server):** Eclipse GlassFish 7
* **Frontend:** JSF (Jakarta Server Faces - Facelets), Bootstrap 5, Özel Vanilla CSS (Inter Fonts, Gradient Hover)
* **Bağımlılık Enjeksiyonu (DI):** CDI (Contexts and Dependency Injection)
* **İş Tarafı (Business Logic):** EJB (Enterprise JavaBeans)
* **Veritabanı ve ORM:** PostgreSQL, JPA (EclipseLink), JNDI Veri Kaynakları (DataSource)
* **Derleme/Kurulum:** Apache Maven

## ⚙️ Kurulum ve Çalıştırma

### 1. Veritabanı (PostgreSQL) Hazırlığı
PostgreSQL üzerinde aşağıdaki komutlarla ana veritabanını oluşturun:
```sql
CREATE DATABASE stackoverflow_db;
```

**Eğer projeyi ilk defa kuruyorsanız (Özellikle Windows Kullanıcıları İçin):**
Proje dizininde yer alan `database_backup.sql` dosyası, uygulamanın çalışması için gerekli olan tüm tablo yapılarını ve örnek verileri barındırır. Veritabanını oluşturduktan sonra bu yedeği içeri aktarmalısınız:
*   **pgAdmin üzerinden:** `stackoverflow_db` veritabanına sağ tıklayıp **Restore** (Geri Yükle) seçeneğini tıklayın ve `database_backup.sql` dosyasını seçerek yükleyin.
*   **Terminal/CMD üzerinden:** `psql -U postgres -d stackoverflow_db -f database_backup.sql` komutunu çalıştırın.

*(Not: Projede JPA tabloları otomatik oluşturma özelliği kapalıdır (`ddl-generation=none`), bu nedenle sistemi çalıştırmadan önce `.sql` dosyasını içeri aktarmanız şarttır).*

### 2. GlassFish 7 Yapılandırması
1. **asadmin** konsolunu kullanarak PostgreSQL JDBC sürücünüzü (`postgresql.jar`) `glassfish/domains/domain1/lib` dizinine ekleyin.
2. `java:app/jdbc/PostgresDS` JNDI adıyla bir PostgreSQL veri kaynağı (Connection Pool & Resource) oluşturun.

### 3. Derleme ve Dağıtım (Deploy)
Proje kök dizininde iken Maven komutu ile `.war` paketi oluşturun ve GlassFish'e aktarın:
```bash
mvn clean package
```
*   **Mac/Linux:** `cp target/stackoverflow-clone-1.0-SNAPSHOT.war glassfish7/glassfish/domains/domain1/autodeploy/`
*   **Windows:** `target` klasörü içinde oluşan `.war` dosyasını kopyalayın ve `glassfish7\glassfish\domains\domain1\autodeploy\` klasörüne elle yapıştırın. (Ya da komut satırında `copy` komutunu kullanabilirsiniz).

### 4. Çalıştırma
Aşağıdaki URL üzerinden sistemi test edebilir, yeni bir kullanıcı kaydı (Departman seçerek) oluşturabilirsiniz:
👉 `http://localhost:8080/stackoverflow-clone-1.0-SNAPSHOT/`

<hr/>
<div align="center">
  <sub>Mühendislik ve özveri ile geliştirilmiştir.</sub>
</div>
# StackOVerflow
