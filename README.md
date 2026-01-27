Social Network Microservices 🚀
Bu proje, modern mikroservis mimarisi standartları kullanılarak geliştirilmiş, ölçeklenebilir bir sosyal ağ backend uygulamasıdır.

🛠 Teknolojiler
Java 21 & Spring Boot 3.4.1

Microservices Architecture

*RabbitMQ: Event-Driven haberleşme ve veri senkronizasyonu.

*Databases: PostgreSQL (Auth Service), MongoDB (User Service).

*Docker & Docker Compose: Konteynerizasyon ve kolay kurulum.

*Zipkin & Micrometer: Dağıtık izleme (Distributed Tracing).

🏗 Mimari Yapı
Proje, servisler arası bağımlılığı minimuma indirmek için Asenkron Haberleşme (Event-Driven) prensibiyle kurgulanmıştır.

*Auth Service: Kullanıcı kimlik doğrulama ve kayıt işlemlerini yönetir.

*User Service: Kullanıcı profillerini ve sosyal etkileşimleri yönetir.

*RabbitMQ: Kayıt, güncelleme ve silme olaylarını servisler arasında asenkron olarak taşır.