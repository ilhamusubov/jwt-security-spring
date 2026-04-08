# jwt-security-spring
# JWT Authentication və Email OTP Təsdiqləmə Sistemi

Bu layihə **Java Spring Boot** istifadə edilərək hazırlanmış təhlükəsiz **authentication və authorization** sistemidir.
Layihədə istifadəçi qeydiyyatı, giriş (login), **JWT token əsaslı authentication**, **email vasitəsilə OTP təsdiqləmə**, refresh token və logout funksionallığı mövcuddur.

## (Features)

- İstifadəçi qeydiyyatı (Register)
- Login sistemi
- JWT ilə authentication
- Email OTP təsdiqləmə
- Refresh token dəstəyi
- Logout funksionallığı
- Role-based authorization
- Validation
- Exception handling
- PostgreSQL inteqrasiyası
  
- ---

## Authentication axını

### 1. Qeydiyyat (Register)
- İstifadəçi email və şifrə ilə qeydiyyatdan keçir
- Sistem istifadəçinin email ünvanına OTP kod göndərir
- İstifadəçi OTP kodu ilə hesabını təsdiqləməlidir

### 2. OTP Təsdiqləmə
- İstifadəçi OTP kodu daxil edir
- Kod düzgündürsə, hesab təsdiqlənmiş hesab olunur

### 3. Login
- Təsdiqlənmiş istifadəçi email və şifrə ilə login olur
- Sistem aşağıdakı tokenləri qaytarır:
  - Access Token
  - Refresh Token

### 4. Refresh Token
- Access token vaxtı bitdikdə, refresh token vasitəsilə yeni access token əldə edilə bilər

### 5. Logout
- İstifadəçi logout etdikdə refresh token etibarsızlaşdırılır
