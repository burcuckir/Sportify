# Sportify


## İçindekiler

- [Proje Hakkında](#proje-hakkında)
- [Özellikler](#özellikler)
- [Kullanılan Teknolojiler](#kullanılan-teknolojiler)
  - [Backend](#backend)
  - [Veritabanı](#veritabanı)
  - [Build & Dependency Management](#build--dependency-management)
  - [API Dokümantasyonu](#api-dokümantasyonu)
  - [Diğerleri](#diğerleri)
- [Kurulum ve Çalıştırma](#kurulum-ve-çalıştırma)
  - [Ön Koşullar](#ön-koşullar)
  - [Kurulum](#kurulum)
- [API Dokümantasyonu](#api-dokümantasyonu)
- [İletişim](#iletişim)


## Proje Hakkında

**Sportify**, sayesinde kullanıcılar, istedikleri spor branşlarında, müsait zaman dilimlerine kolayca randevu oluşturabilirler. Sportify'in ana amacı, spor aktivitelerini organize etmeyi ve tesis kullanımını daha verimli hale getirmeyi sağlamaktır.

## Özellikler

- **Kullanıcı Yönetimi**
    - Kayıt ve giriş işlemleri

- **Aktivite Yönetimi**
    - Spor aktivitelerinin eklenmesi, düzenlenmesi ve silinmesi
    - Aktivite türlerine göre filtreleme
  
-  **Kolay Randevu Oluşturma**
    - Kullanıcılar, tercih ettikleri spor branşını seçerek uygun zaman dilimlerinde hızlıca rezervasyon yapabilirler.

- **API Dokümantasyonu**
    - Swagger entegrasyonu ile detaylı API dökümantasyonu

### Kullanılan Teknolojiler

#### Backend

- **[Java 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)**
- **[Spring Boot 3.4.0](https://spring.io/projects/spring-boot)**
- **[Spring Security](https://spring.io/projects/spring-security)**
- **[Spring Data JPA](https://spring.io/projects/spring-data-jpa)**
- **[Spring Cloud Gateway](https://spring.io/projects/spring-cloud-gateway)**
- **[JWT (JSON Web Tokens)](https://jwt.io/)**
- **[Lombok](https://projectlombok.org/)**
- **[JAXB API](https://javaee.github.io/jaxb-v2/)**
- **[Spring Context](https://spring.io/projects/spring-framework)**
- **[Jackson Databind](https://github.com/FasterXML/jackson-databind)**
- **[Spring AMQP](https://spring.io/projects/spring-amqp)**
- **[AspectJ Weaver](https://www.eclipse.org/aspectj/)**
- **[Tomcat Embedded](https://tomcat.apache.org/)**
- **[PostgreSQL](https://www.postgresql.org/)**
- **[Logstash Logback Encoder](https://github.com/logstash/logstash-logback-encoder)**

#### Veritabanı
- **[PostgreSQL](https://www.postgresql.org/)**

#### Build & Dependency Management
- **[Maven](https://maven.apache.org/)**

#### API Dokümantasyonu
- **[Springdoc OpenAPI](https://springdoc.org/)**

#### Diğerleri
- **[Docker](https://www.docker.com/)**
- **[Kibana](https://www.elastic.co/kibana)**
- **[Elasticsearch](https://www.elastic.co/elasticsearch)**
- **[RabbitMQ](https://www.rabbitmq.com/)**
- **[WireMock](https://wiremock.org/)**
## Kurulum ve Çalıştırma

Aşağıdaki adımları izleyerek Sportify uygulamasını yerel makinenizde kurabilir ve çalıştırabilirsiniz.

### Ön Koşullar

Sportify uygulamasını geliştirmek ve çalıştırmak için aşağıdaki yazılımların sisteminizde kurulu olması gerekmektedir:

- **[Java JDK 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)**
- **[Maven](https://maven.apache.org/download.cgi)**
- **[PostgreSQL](https://www.postgresql.org/download/)**
- **[RabbitMQ](https://www.rabbitmq.com/download.html)**
- **[Elasticsearch](https://www.elastic.co/downloads/elasticsearch)**
- **[Kibana](https://www.elastic.co/downloads/kibana)**
- **[WireMock](http://wiremock.org/docs/getting-started/)**
- **[Docker](https://www.docker.com/get-started)** (Opsiyonel)
### Kurulum

1. **Depoyu Klonlayın**

   ```bash
   git clone https://github.com/burcuckir/Sportify.git
   cd Sportify
2. **Docker Kullanarak (Docker'ın kurulu ve çalışır durumda olduğundan emin olun.)**
- **Projede kullanılan tüm hizmetleri ve bağımlılıkları otomatik olarak başlatmak için aşağıdaki komutu çalıştırın.**
   ```bash
   docker compose up

## API Dokümantasyonu

Sportify, tüm işlevsellikler için RESTful API sunmaktadır. Detaylı API dokümantasyonuna Swagger UI üzerinden aşağıdaki bağlantılar aracılığıyla erişebilirsiniz:

- **User API:** http://localhost:8081/swagger-ui/index.html#/
- **Reservation API:** http://localhost:8082/swagger-ui/index.html#/
- **Payment API:** http://localhost:8084/swagger-ui/index.html#/

## İletişim

**Yazar:** Burcu Çakır  
**E-posta:** [burcuckir@gmail.com](mailto:burcuckir@gmail.com)  
**GitHub:** [https://github.com/burcuckir](https://github.com/burcuckir)  
**LinkedIn:** [https://www.linkedin.com/in/burcu-cakir/](https://www.linkedin.com/in/burcu-cakir/)

Herhangi bir sorunuz, öneriniz veya geri bildiriminiz olursa lütfen benimle iletişime geçmekten çekinmeyin!
