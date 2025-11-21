# Recruitment Management System

Hệ thống quản lý tuyển dụng với luồng tạo job đa bước, cấu hình vòng tuyển dụng và email templates tự động.

## 📋 Mô Tả

Ứng dụng web cho phép tạo bài đăng tuyển dụng với các tính năng:
- Tạo thông tin job cơ bản (tiêu đề, mức lương, địa chỉ, kinh nghiệm, v.v.)
- Cấu hình số vòng tuyển dụng và tên từng vòng
- Tạo và quản lý email templates tự động (Pass/Fail) cho từng vòng
- Hỗ trợ placeholders động trong email templates

## 🏗️ Kiến Trúc

### Frontend (Ionic Angular)
- **Framework**: Ionic 8 + Angular 20 (Standalone Components)
- **Port**: 8100
- **Location**: `recruitment-app/`

### Backend (Spring Boot)
- **Framework**: Spring Boot 4.0
- **Port**: 8080
- **Database**: MySQL
- **Location**: `recruitment-backend/`

## 🚀 Cài Đặt và Chạy

### Yêu Cầu Hệ Thống

#### Backend
- Java 17+
- Maven 3.6+
- MySQL Server
- Database: `recruitment_db`

#### Frontend
- Node.js 18+
- npm hoặc yarn
- Ionic CLI

### Bước 1: Cấu Hình Database

```sql
CREATE DATABASE IF NOT EXISTS recruitment_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

Cập nhật thông tin kết nối trong `recruitment-backend/recruitment-backend/src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/recruitment_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=your_username
spring.datasource.password=your_password
server.port=8080
```

### Bước 2: Chạy Backend

```bash
cd recruitment-backend/recruitment-backend
./mvnw spring-boot:run
# Hoặc trên Windows:
mvnw.cmd spring-boot:run
```

Backend sẽ chạy tại: http://localhost:8080

### Bước 3: Chạy Frontend

```bash
cd recruitment-app
npm install  # Lần đầu tiên
ionic serve
```

Frontend sẽ chạy tại: http://localhost:8100

### Scripts Tự Động (Windows)

- `chay-backend.bat` - Chạy backend
- `chay-frontend.bat` - Chạy frontend
- `chay-ca-hai.bat` - Chạy cả backend và frontend

## 📱 Luồng Ứng Dụng

### 1. Màn Hình: TẠO BÀI ĐĂNG (Create Job)
**Route**: `/create-job`

**Chức năng**:
- Nhập thông tin job: Tiêu đề, Mức lương (Từ/Đến), Địa chỉ, Kinh nghiệm, Trạng thái, Hạn nộp đơn, Số vòng tuyển dụng, Nội dung
- Validate form
- Lưu vào `JobService.tempData.job`
- Navigate đến `/rounds-config`

**Fields**:
- Tiêu đề (required)
- Mức lương từ/đến (required)
- Địa chỉ (required)
- Số kinh nghiệm tối thiểu + Đơn vị (Năm/Tháng) (required)
- Trạng thái (Draft/Active/Closed) (required)
- Hạn bài đăng (required)
- Vòng tuyển dụng (required, min: 1)
- Nội dung (required)

### 2. Màn Hình: VÒNG TUYỂN DỤNG (Rounds Config)
**Route**: `/rounds-config`

**Chức năng**:
- Load `roundCount` từ màn hình 1
- Dynamic form với FormArray để cấu hình từng vòng
- Mỗi vòng có: Tên vòng, Button "Mẫu email PASS/FAIL", Toggle "Xác nhận của ứng viên"
- Lưu vào `JobService.tempData.rounds`
- Navigate đến `/email-templates`

**Fields per Round**:
- Tên vòng (required)
- Toggle: Xác nhận của ứng viên (boolean)

### 3. Màn Hình: EMAIL TỰ ĐỘNG (Email Templates)
**Route**: `/email-templates`

**Chức năng**:
- Hiển thị tất cả các vòng đã cấu hình
- Cho mỗi vòng: Button "Mẫu email Pass" và "Mẫu email Fail"
- Click vào button → Hiển thị list samples từ API
- Click sample hoặc "Tạo mới" → Navigate đến `/template-editor`
- Hiển thị trạng thái "Đã cấu hình" / "Chưa cấu hình" cho mỗi template
- Button "Xác Nhận Tạo Job" → Validation → Popup confirm → Gọi API tạo job

**API Calls**:
- `GET /api/samples/pass` - Lấy danh sách mẫu email Pass
- `GET /api/samples/fail` - Lấy danh sách mẫu email Fail

### 4. Màn Hình: TẠO MAIL (Template Editor)
**Route**: `/template-editor`

**Chức năng**:
- Editor cho email template
- Hiển thị list samples (nếu có)
- Form: Tên form, Tiêu đề, Nội dung
- Hỗ trợ placeholders: `{CandidateName}`, `{RoundName}`, `{JobTitle}`, `{CompanyName}`, `{NextRoundName}`, `{CompanyEmail}`, `{DateTime}`
- Lưu vào `JobService.tempData.rounds.templates[roundIndex][type]Template`
- Navigate quay lại `/email-templates`

**Query Params**:
- `roundIndex`: Index của vòng (0-based)
- `type`: 'pass' hoặc 'fail'
- `sampleName`, `sampleSubject`, `sampleContent`: (optional) Data từ sample được chọn

### 5. Popup: Xác Nhận Tạo Job
**Chức năng**:
- Validation: Kiểm tra tất cả templates đã được cấu hình
- Alert dialog: "Bạn chắc chưa???"
- Gọi API `POST /api/jobs` với payload đầy đủ
- Hiển thị `applyUrl` khi thành công
- Clear temp data và quay về màn hình 1

## 🔌 API Endpoints

### Backend API Base: `http://localhost:8080/api`

#### 1. Tạo Job
```
POST /api/jobs
Content-Type: application/json

Request Body:
{
  "businessId": 1,
  "title": "Software Engineer",
  "description": "Job description...",
  "location": "Ho Chi Minh City",
  "salaryFrom": 10000000,
  "salaryTo": 20000000,
  "yoe": 2.0,
  "yoeUnit": "Năm",
  "status": "Active",
  "deadline": "2024-12-31T00:00:00",
  "roundCount": 3,
  "rounds": [
    {
      "roundName": "Vòng CV",
      "isConfirmed": false
    },
    ...
  ],
  "templates": [
    {
      "passTemplate": {
        "formName": "Pass Template 1",
        "subject": "Congratulations!",
        "content": "Dear {CandidateName}..."
      },
      "failTemplate": {
        "formName": "Fail Template 1",
        "subject": "Thank you for applying",
        "content": "Dear {CandidateName}..."
      }
    },
    ...
  ]
}

Response:
{
  "id": 1,
  "title": "Software Engineer",
  "applyUrl": "https://yourapp.com/apply/1-abc12345",
  ...
}
```

#### 2. Lấy Samples
```
GET /api/samples/pass
GET /api/samples/fail

Response: [
  {
    "id": 1,
    "sampleName": "Form pass 1",
    "subject": "Congratulations!",
    "content": "Dear {CandidateName}..."
  },
  ...
]
```

## 📁 Cấu Trúc Project

### Frontend (`recruitment-app/`)
```
src/app/
├── create-job/              # Màn hình 1: Tạo bài đăng
│   ├── create-job.page.ts
│   ├── create-job.page.html
│   └── create-job.module.ts
├── rounds-config/           # Màn hình 2: Cấu hình vòng
│   ├── rounds-config.page.ts
│   ├── rounds-config.page.html
│   └── rounds-config.module.ts
├── email-templates/         # Màn hình 3: Email templates
│   ├── email-templates.page.ts
│   ├── email-templates.page.html
│   └── email-templates.module.ts
├── template-editor/         # Màn hình 4: Editor template
│   ├── template-editor.page.ts
│   ├── template-editor.page.html
│   └── template-editor.module.ts
├── services/
│   └── job.service.ts       # Service cho API calls và temp data
└── app.routes.ts            # Routing configuration
```

### Backend (`recruitment-backend/`)
```
src/main/java/com/test/recruitment_backend/
├── controller/
│   └── JobController.java   # REST API endpoints
├── service/
│   └── JobService.java      # Business logic
├── repository/              # JPA Repositories
├── entity/                 # Database entities
│   ├── JobPost.java
│   ├── JobRound.java
│   ├── EmailTemplate.java
│   └── EmailSampleTemplate.java
└── dto/                    # Data Transfer Objects
    ├── JobCreateDto.java
    ├── RoundDto.java
    └── TemplateDto.java
```

## 🔧 Cấu Hình

### CORS
Backend đã được cấu hình CORS để cho phép requests từ `http://localhost:8100`:

```java
@CrossOrigin(origins = "http://localhost:8100")
```

### Database Schema
Backend tự động tạo tables khi khởi động (do `spring.jpa.hibernate.ddl-auto=update`).

**Tables**:
- `Job_Post` - Thông tin job
- `Job_Round` - Các vòng tuyển dụng
- `Email_Template` - Templates cho từng vòng
- `Email_Sample_Template` - Mẫu templates sẵn có
- `Form` - Form types (PASS/FAIL)
- `Business` - Thông tin business

## 🧪 Testing

### Test Luồng Hoàn Chỉnh:

1. **Màn hình 1**: 
   - Nhập đầy đủ thông tin (ví dụ: roundCount = 3)
   - Click "Confirm"
   - → Chuyển sang màn hình 2

2. **Màn hình 2**:
   - Kiểm tra số vòng = 3 (tự động load từ màn hình 1)
   - Nhập tên cho từng vòng
   - Click "Confirm"
   - → Chuyển sang màn hình 3

3. **Màn hình 3**:
   - Kiểm tra hiển thị đủ 3 vòng
   - Click "Mẫu email Pass" của Vòng 1
   - → Hiển thị list samples
   - Click một sample hoặc "Tạo mới"
   - → Chuyển sang màn hình 4

4. **Màn hình 4**:
   - Nhập/chỉnh sửa template
   - Click "Confirm"
   - → Quay lại màn hình 3
   - Kiểm tra hiển thị "✓ Đã cấu hình" cho template vừa tạo

5. **Tạo Job**:
   - Cấu hình đủ templates (hoặc bỏ qua validation)
   - Click "Xác Nhận Tạo Job"
   - → Popup confirm
   - Click "Xác nhận"
   - → Gọi API, hiển thị `applyUrl`

## 🐛 Xử Lý Lỗi Thường Gặp

### Lỗi: "No provider found for ModalController"
**Giải pháp**: Đã thay modal bằng page navigation. Không còn sử dụng ModalController.

### Lỗi: "Cannot find control"
**Giải pháp**: Đã sửa FormArray binding, sử dụng `[formGroup]` thay vì `[formGroupName]`.

### Lỗi: "Port already in use"
```bash
# Windows
netstat -ano | findstr :8080
taskkill /PID <PID> /F

# Hoặc đổi port trong application.properties
```

### Lỗi: "CORS policy"
Backend đã được cấu hình CORS. Nếu vẫn lỗi, kiểm tra:
- Backend đang chạy tại port 8080
- Frontend đang chạy tại port 8100
- URL trong `JobService.apiUrl` đúng

## 📝 Notes

- **Temp Data**: Data được lưu tạm trong `JobService.tempData` giữa các màn hình
- **Standalone Components**: Tất cả components sử dụng Angular standalone mode
- **Form Validation**: Sử dụng ReactiveForms với validators
- **Placeholders**: Email templates hỗ trợ placeholders động như `{CandidateName}`, `{RoundName}`, v.v.

## 👥 Contributors

- Development Team

## 📄 License

Private Project

---

**Last Updated**: 2025-11-21

