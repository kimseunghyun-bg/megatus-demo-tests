import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Test {
    static final String FROM = "tptkddlf@naver.com";
    static final String FROMNAME = "kimsh-naver";
    static final String TO = "kimseunghyun@megatus.com";

    static final String SMTP_USERNAME = "tptkddlf";
    static final String SMTP_PASSWORD = "etg2a8hi@NAV";

    static final String HOST = "smtp.naver.com";
    static final int PORT = 587;

    static final String SUBJECT = "메일 제목";

    static final String BODY = String.join(
            System.getProperty("line.separator"),
            "<h1>인증번호</h1>",
            "<p>123123</p>."
    );

    public static void main(String[] args) throws Exception {
        Properties props = System.getProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.port", PORT);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);

        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(FROM, FROMNAME));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
        msg.setSubject(SUBJECT);
        msg.setContent(BODY, "text/html;charset=euc-kr");

        Transport transport = session.getTransport();

        try {
            System.out.println("Sending...");

            transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
            transport.sendMessage(msg, msg.getAllRecipients());

            System.out.println("Email sent!");
        } catch (Exception ex) {
            ex.printStackTrace();

        } finally {
            transport.close();
        }
    }

    /**
     * 자바 메일 발송
     *
     * @throws MessagingException
     * @throws AddressException
     **/
//    @RequestMapping(value = "/mailSender")
    public void mailSender(/*HttpServletRequest request, ModelMap mo*/) throws AddressException, MessagingException {

        // 네이버일 경우 smtp.naver.com 을 입력합니다.
        // Google일 경우 smtp.gmail.com 을 입력합니다.
        String host = "smtp.naver.com";

        final String username = "XXXXXXXX"; //네이버 아이디를 입력해주세요. @nave.com은 입력하지 마시구요.
        final String password = "YYYYYYYY"; //네이버 이메일 비밀번호를 입력해주세요.
        int port = 465; //포트번호

        // 메일 내용
        String recipient = "WWWWWWW@gmail.com"; //받는 사람의 메일주소를 입력해주세요.
        String subject = "메일테스트"; //메일 제목 입력해주세요.
        String body = username + "님으로 부터 메일을 받았습니다."; //메일 내용 입력해주세요.

        Properties props = System.getProperties(); // 정보를 담기 위한 객체 생성

        // SMTP 서버 정보 설정
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.trust", host);

        //Session 생성
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            String un = username;
            String pw = password;

            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(un, pw);
            }
        });
        session.setDebug(true);
        //for debug
        Message mimeMessage = new MimeMessage(session); //MimeMessage 생성
        mimeMessage.setFrom(new InternetAddress("XXXXXXXX@naver.com")); //발신자 셋팅 , 보내는 사람의 이메일주소를 한번 더 입력합니다. 이때는 이메일 풀 주소를 다 작성해주세요.
        mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient)); //수신자셋팅 //.TO 외에 .CC(참조) .BCC(숨은참조) 도 있음
        mimeMessage.setSubject(subject); //제목셋팅
        mimeMessage.setText(body); //내용셋팅
        Transport.send(mimeMessage); //javax.mail.Transport.send() 이용
    }
}
