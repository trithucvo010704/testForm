# Hướng Dẫn Chạy Frontend và Backend

## 📋 Yêu Cầu Hệ Thống

### Backend (Spring Boot)
- ✅ Java 17 hoặc cao hơn
- ✅ Maven 3.6+
- ✅ MySQL Server
- ✅ Database: `recruitment_db`

### Frontend (Ionic Angular)
- ✅ Node.js 18+ và npm
- ✅ Ionic CLI (sẽ cài đặt nếu chưa có)

---

## 🗄️ Bước 1: Cấu Hình Database

### 1.1. Tạo Database MySQL

Mở MySQL và chạy lệnh:

```sql
CREATE DATABASE IF NOT EXISTS recruitment_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 1.2. Kiểm Tra Thông Tin Kết Nối

File `recruitment-backend/recruitment-backend/src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/recruitment_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=vothuc
spring.datasource.password=tuanthuc@1
server.port=8080
```

**⚠️ Lưu ý:** Nếu username/password khác, hãy sửa trong file `application.properties`.

---

## 🚀 Bước 2: Chạy Backend (Spring Boot)

### 2.1. Mở Terminal 1 - Chạy Backend

```bash
# Di chuyển vào thư mục backend
cd recruitment-backend/recruitment-backend

# Chạy Spring Boot (chọn một trong các cách)
# Cách 1: Dùng Maven Wrapper (khuyến nghị)
./mvnw spring-boot:run

# Hoặc trên Windows:
mvnw.cmd spring-boot:run

# Cách 2: Dùng Maven (nếu đã cài Maven)
mvn spring-boot:run

# Cách 3: Build JAR và chạy
mvn clean package
java -jar target/recruitment-backend-0.0.1-SNAPSHOT.jar
```

### 2.2. Kiểm Tra Backend Đã Chạy

Mở browser và truy cập:
- **API Base URL:** http://localhost:8080/api
- **Health Check:** http://localhost:8080/api/jobs (nếu có endpoint test)

Backend sẽ tự động tạo tables trong database khi khởi động (do `spring.jpa.hibernate.ddl-auto=update`).

---

## 🎨 Bước 3: Chạy Frontend (Ionic Angular)

### 3.1. Cài Đặt Dependencies (Lần Đầu)

```bash
# Di chuyển vào thư mục frontend
cd recruitment-app

# Cài đặt node_modules (nếu chưa có)
npm install
```

### 3.2. Mở Terminal 2 - Chạy Frontend

```bash
# Đảm bảo đang ở thư mục recruitment-app
cd recruitment-app

# Chạy Ionic dev server
ionic serve

# Hoặc dùng Angular CLI
ng serve
```

### 3.3. Kiểm Tra Frontend Đã Chạy

Frontend sẽ tự động mở browser tại:
- **URL:** http://localhost:8100

---

## 🔧 Cấu Hình CORS (Nếu Cần)

Nếu gặp lỗi CORS khi frontend gọi API, thêm vào Backend:

**File:** `recruitment-backend/recruitment-backend/src/main/java/.../RecruitmentBackendApplication.java`

```java
@SpringBootApplication
public class RecruitmentBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(RecruitmentBackendApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("http://localhost:8100")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*");
            }
        };
    }
}
```

---

## 📝 Tóm Tắt Các Lệnh

### Terminal 1 - Backend:
```bash
cd recruitment-backend/recruitment-backend
./mvnw spring-boot:run
# Hoặc trên Windows: mvnw.cmd spring-boot:run
```

### Terminal 2 - Frontend:
```bash
cd recruitment-app
ionic serve
```

---

## ✅ Kiểm Tra Kết Nối

1. **Backend:** http://localhost:8080/api
2. **Frontend:** http://localhost:8100
3. **Database:** MySQL đang chạy và có database `recruitment_db`

---

## 🐛 Xử Lý Lỗi Thường Gặp

### Lỗi: "Port 8080 already in use"
```bash
# Tìm process đang dùng port 8080
netstat -ano | findstr :8080
# Kill process (thay PID bằng process ID)
taskkill /PID <PID> /F
```

### Lỗi: "Cannot connect to MySQL"
- Kiểm tra MySQL đang chạy
- Kiểm tra username/password trong `application.properties`
- Kiểm tra database `recruitment_db` đã được tạo

### Lỗi: "npm install failed"
```bash
# Xóa node_modules và cài lại
rm -rf node_modules package-lock.json
npm install
```

### Lỗi: "CORS policy"
- Thêm CORS configuration vào Backend (xem phần trên)

---

## 🎯 Luồng Test

1. Mở http://localhost:8100 → Tự động redirect đến `/create-job`
2. Nhập **Title** và **Description** → Click "Tiếp Theo"
3. Chọn **Số vòng** và nhập tên vòng → Click "Tiếp Theo"
4. Click **"Form Pass"** hoặc **"Form Fail"** → Chọn template → Confirm
5. Click **"Xác Nhận Tạo Job"** → Kiểm tra response từ API

---

## 📞 Thông Tin Liên Hệ

- **Backend Port:** 8080
- **Frontend Port:** 8100
- **Database:** MySQL localhost:3306/recruitment_db

