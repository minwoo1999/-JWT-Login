# JWT-login

### JWT 토큰을 이용해서 로그인 및 검증


* JWT란 무엇이고 어느상황에서 JWT를 활용하는지, JWT 토큰 생성 및 토큰 가져오기 구현.


https://minwoo-it-factory.tistory.com/72


### POSTMAN을사용해서 토큰생성 후 Header에 Authorization에 해당하는 토큰을 입력.

* JWT토큰을 생성하고 토큰검증 및 예외처리까지 구현완료

### React와 Spring boot 연동하여 로그인 성공시 token 발행 및 API 검증



* login Success: react 에서 sessionStorage.setItem("tokenId", jwttoken); jwt토큰을 세션스토리지에 저장

* API검증시:

  headers: {
            Authorization: `${sessionStorage.getItem("tokenId")}`
        }

* Controller에서 Token을 검증하고 Payload에 담겨있던 사용자권한 데이터를 출력해준다.

### Controller에서 Hedaer Authnication값을 가져오는방법

@RequestMapping(value = "/mypage", method = RequestMethod.GET)
    @CrossOrigin(origins = "http://localhost:3000")
    public User getAuthInfo(HttpServletRequest req) {
        String authorization = req.getHeader("Authorization"); ##authorization 값 가져오기
        String email=securityService.getSubject(authorization); ## authorization 값으로 토큰검증 후 사용자권한데이터(이메일)



        User user=loginService.getUser(email); ## 해당하는 이메일로 사용자정보가져오기

        return user; ## 사용자정보불러오기
    }



