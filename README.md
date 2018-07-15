# BookFinder

# 개요
* [카카오 책 검색 API](https://developers.kakao.com/docs/restapi/search#책-검색) 를 활용한 서비스

# 주요기능
* 로그인/회원가입
* 책 검색
* 최근 검색 히스토리
* 책 상세정보
* 북마크
  
# 실행환경
* JDK 8.x
* Spring Framework 4.x
* Maven
* Tomcat 8.x
* Oracle 11g

# 참조 라이브러리
* [json-lib, json-lib-ext-spring](http://json-lib.sourceforge.net/)
* [httpclient](https://hc.apache.org/httpcomponents-client-4.5.x/index.html)
* [Jackson](https://github.com/FasterXML)
* [aopaliance](http://aopalliance.sourceforge.net/)
* [aspectjweaver](https://www.eclipse.org/aspectj/)
* [dbcp2](http://commons.apache.org/proper/commons-dbcp/)
* [slf4j](https://www.slf4j.org/)
* [logback](https://logback.qos.ch/)
* [log4jdbc](http://code.google.com/p/log4jdbc-remix/)
* [taglibs](https://tomcat.apache.org/taglibs/standard/), [jstl](https://jcp.org/en/jsr/detail?id=52/)

# 프로퍼티 설정
* [ Oracle 접속정보 설정 ] database.properties<br/>
  * jdbc.url : 접속기술자
    * SSID 기반 설정 : jdbc:oracle:thin:@host:port:ssid
    * Service Name 기반 설정 : jdbc:oracle:thin:@//host:port/serviceName
  * jdbc.username : 스키마 유저
  * jdbc.password : 유저 비밀번호
* [ 서버 기본 환경 설정 ] envrironment.properties<br/>
  * REQUEST_* : httpclient Request 설정
  * SOCKET_* : httpclient Socket 설정
  * POOLED_* : PoolingHttpClientConnection 설정
  * HTTP_CLIENT_* : httpclient 설정
  * HTTP_SESSION_TIME_OUT : 세션 유효시간 설정
  * API_AUTH_KEY : [카카오 API Key](https://developers.kakao.com/docs/restapi#개발환경-구성) 설정
  * REST_API_URL : [카카오 책 검색 API Url](https://developers.kakao.com/docs/restapi/search#책-검색) 설정
  
* [ 로깅 설정 ] logback.xml<br/>
  * LOG_HOME : 로그 홈 디렉토리
  * LOG_NAME : 로그명
  * FILE_OUT : 로그파일 rolling 설정
    * 각 logger에 appender-ref="FILE_OUT" 주석을 해제해야 로그파일이 write 된다.
  * Log Level: TRACE > DEBUG > INFO > WARN > ERROR
  
# 테이블 생성
* CREATE_KBOOKS_TABLE.sql 를 실행

# 실행
  1. STS(Spring Tool Suite) 다운로드 및 설치
  2. Git clone https://github.com/Henrry-J/BookFinder -- 레포지토리에서 프로젝트 복사
  3. # 프로퍼티 설정 진행
  4. # 테이블 생성 진행
  5. Maven Install
  4. Run on Server > Tomcat v8.0 Server 선택
  5. http://localhost:8080/KBOOKS/ 접속 확인
