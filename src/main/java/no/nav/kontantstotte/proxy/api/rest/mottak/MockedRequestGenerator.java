package no.nav.kontantstotte.proxy.api.rest.mottak;

import no.nav.kontantstotte.innsending.domene.Soknad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class MockedRequestGenerator {
    private static final Logger log = LoggerFactory.getLogger(MockedRequestGenerator.class);

    public static Soknad soknad() {
        return new Soknad(tilfeldigFodselsnummer(50), loadMockedPdf());
    }

    private static byte[] loadMockedPdf() {

        try(InputStream is = MockedRequestGenerator.class.getClassLoader().getResourceAsStream("mock/dummy.pdf")) {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            byte[] buffer = new byte[4096];
            int read;
            while((read = is.read(buffer)) != -1) {
                baos.write(buffer, 0, read);

            }
            baos.flush();
            return baos.toByteArray();
        } catch (IOException e) {
            log.error("Kunne ikke laster mock-pdf", e);
            throw new IllegalStateException(e);
        }
    }


    private static String tilfeldigFodselsnummer(int approxAge) {
        Random random = new Random();
        String birthDate = LocalDate.now().plusMonths(6)
                .minusYears(approxAge)
                .minusMonths(random.nextInt(12))
                .minusDays(random.nextInt(31))
                .format(DateTimeFormatter.ofPattern("ddMMyy"));

        String birthNumber = String.format("%03d", random.nextInt(499));

        try {
            return calculateChecksum(birthDate + birthNumber);
        } catch (IllegalStateException e) {
            return tilfeldigFodselsnummer(approxAge);
        }
    }

    private static String calculateChecksum(String nineDigits) {

        int k1 = 11 - ((3 * digit(0, nineDigits) + 7 * digit(1, nineDigits) + 6 * digit(2, nineDigits) + 1 * digit(3, nineDigits) + 8 * digit(4, nineDigits) + 9 * digit(5, nineDigits) + 4 * digit(6, nineDigits) + 5 * digit(7, nineDigits) + 2 * digit(8, nineDigits)) % 11);
        int k2 = 11 - ((5 * digit(0, nineDigits) + 4 * digit(1, nineDigits) + 3 * digit(2, nineDigits) + 2 * digit(3, nineDigits) + 7 * digit(4, nineDigits) + 6 * digit(5, nineDigits) + 5 * digit(6, nineDigits) + 4 * digit(7, nineDigits) + 3 * digit(8, nineDigits) + 2 * k1) % 11);

        if(k1 == 10 || k2 == 10)
            throw new IllegalStateException("Ugyldig fødselsnummer, prøv et annet");
        
        return nineDigits + k1 + k2;
    }
    
    private static int digit(int digit, String s) {
        return Integer.parseInt(s.substring(digit, digit + 1));
    }
    
    public static void main(String[] args) {
        System.out.println(soknad());
    }


}
