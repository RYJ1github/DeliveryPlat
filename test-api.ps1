# Test API Script
Write-Host "Testing Yujie Delivery API..." -ForegroundColor Green

# Test 1: Register a new user
Write-Host "`n1. Testing User Registration..." -ForegroundColor Yellow
$registerBody = @{
    username = "testuser"
    email = "test@example.com"
    password = "password123"
    role = "CUSTOMER"
} | ConvertTo-Json

try {
    $response = Invoke-RestMethod -Uri "http://localhost:8080/api/v1/auth/signup" -Method POST -Body $registerBody -ContentType "application/json"
    Write-Host "✅ Registration successful!" -ForegroundColor Green
    Write-Host "Response: $($response | ConvertTo-Json)" -ForegroundColor Cyan
} catch {
    Write-Host "❌ Registration failed: $($_.Exception.Message)" -ForegroundColor Red
}

# Test 2: Login
Write-Host "`n2. Testing User Login..." -ForegroundColor Yellow
$loginBody = @{
    username = "testuser"
    password = "password123"
} | ConvertTo-Json

try {
    $loginResponse = Invoke-RestMethod -Uri "http://localhost:8080/api/v1/auth/login" -Method POST -Body $loginBody -ContentType "application/json"
    Write-Host "✅ Login successful!" -ForegroundColor Green
    Write-Host "Token: $($loginResponse.token)" -ForegroundColor Cyan
    
    # Store token for other tests
    $token = $loginResponse.token
} catch {
    Write-Host "❌ Login failed: $($_.Exception.Message)" -ForegroundColor Red
    $token = $null
}

# Test 3: Get user profile (if login was successful)
if ($token) {
    Write-Host "`n3. Testing Get User Profile..." -ForegroundColor Yellow
    $headers = @{
        "Authorization" = "Bearer $token"
    }
    
    try {
        $profileResponse = Invoke-RestMethod -Uri "http://localhost:8080/api/v1/users/profile" -Method GET -Headers $headers
        Write-Host "✅ Get profile successful!" -ForegroundColor Green
        Write-Host "Profile: $($profileResponse | ConvertTo-Json)" -ForegroundColor Cyan
    } catch {
        Write-Host "❌ Get profile failed: $($_.Exception.Message)" -ForegroundColor Red
    }
}

Write-Host "`nAPI Testing completed!" -ForegroundColor Green 