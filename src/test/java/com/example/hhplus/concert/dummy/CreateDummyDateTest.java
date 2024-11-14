package com.example.hhplus.concert.dummy;

import static com.example.hhplus.concert.util.FixtureCommon.SUT;

import com.example.hhplus.concert.domain.concert.model.Concert;
import com.example.hhplus.concert.domain.concert.model.ConcertSchedule;
import com.example.hhplus.concert.domain.concert.model.ConcertSeat;
import com.example.hhplus.concert.domain.concert.model.Reservation;
import com.example.hhplus.concert.domain.concert.model.ReservationStatus;
import com.example.hhplus.concert.domain.payment.model.Payment;
import com.example.hhplus.concert.domain.user.model.User;
import com.example.hhplus.concert.domain.user.model.Wallet;
import com.example.hhplus.concert.infra.db.concert.ConcertJpaRepository;
import com.example.hhplus.concert.infra.db.concert.ConcertScheduleJpaRepository;
import com.example.hhplus.concert.infra.db.concert.ConcertSeatJpaRepository;
import com.example.hhplus.concert.infra.db.concert.ReservationJpaRepository;
import com.example.hhplus.concert.infra.db.payment.PaymentJpaRepository;
import com.example.hhplus.concert.infra.db.user.UserJpaRepository;
import com.example.hhplus.concert.infra.db.user.WalletJpaRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

@Disabled
@SpringBootTest
@ActiveProfiles("local")
class CreateDummyDateTest {

  @Autowired
  private UserJpaRepository userJpaRepository;

  @Autowired
  private WalletJpaRepository walletJpaRepository;

  @Autowired
  private ConcertJpaRepository concertJpaRepository;

  @Autowired
  private ConcertScheduleJpaRepository concertScheduleJpaRepository;

  @Autowired
  private ConcertSeatJpaRepository concertSeatJpaRepository;

  @Autowired
  private ReservationJpaRepository reservationJpaRepository;

  @Autowired
  private PaymentJpaRepository paymentJpaRepository;


  @Test
  void createDummyUserData() {
    final int count = 10 * 10000;
    final List<User> users = userJpaRepository.saveAll(SUT.giveMe(User.class, count));
    walletJpaRepository.saveAll(
        users.stream()
            .map(user -> SUT.giveMeBuilder(Wallet.class)
                .set("userId", user.getId())
                .set("version", 0L)
                .build().sample())
            .toList());
  }

  @Test
  void createDummyConcertData() {
    final int count = 10 * 10000;
    final int concertForScheduleCount = 3;

    final List<Concert> concerts = concertJpaRepository.saveAll(SUT.giveMe(Concert.class, count));

    concertScheduleJpaRepository.saveAll(
        concerts.stream()
            .flatMap(concert ->
                IntStream.range(0, concertForScheduleCount)
                    .mapToObj(i -> SUT.giveMeBuilder(ConcertSchedule.class)
                        .set("concertId", concert.getId())
                        .build().sample())
            )
            .toList()
    );
  }

  @Test
  void createDummyConcertSeatData() {
    final int scheduleForSeatCount = 1;
    final List<ConcertSchedule> concertSchedules = concertScheduleJpaRepository.findAll();

    concertSeatJpaRepository.saveAll(
        concertSchedules.stream()
            .flatMap(concertSchedule ->
                IntStream.range(0, scheduleForSeatCount)
                    .mapToObj(i -> SUT.giveMeBuilder(ConcertSeat.class)
                        .set("concertScheduleId", concertSchedule.getId())
                        .set("version", 0L)
                        .build().sample())
            )
            .toList()
    );
  }

  @Test
  void createDummyReservationData() {
    final int junkSize = 1000;
    int pageNumber = 0;
    List<Reservation> reservationsJunk = new ArrayList<>(junkSize);

    while (true) {
      Page<ConcertSeat> concertSeatsPage = concertSeatJpaRepository.findAll(
          PageRequest.of(pageNumber, junkSize)
      );

      List<ConcertSeat> concertSeats = concertSeatsPage.getContent();

      if (concertSeats.isEmpty()) {
        break;
      }

      for (ConcertSeat concertSeat : concertSeats) {
        if (concertSeat.getIsReserved()) {
          Reservation reservation = SUT.giveMeBuilder(Reservation.class)
              .set("concertSeatId", concertSeat.getId())
              .set("version", 0L)
              .build().sample();
          reservationsJunk.add(reservation);

          if (reservationsJunk.size() == junkSize) {
            reservationJpaRepository.saveAll(reservationsJunk);
            reservationsJunk.clear();
          }
        }
      }

      pageNumber++;
    }

    if (!reservationsJunk.isEmpty()) {
      reservationJpaRepository.saveAll(reservationsJunk);
    }
  }


  @Test
  void createDummyPaymentData() {
    final int junkSize = 1000;
    int pageNumber = 0;
    List<Payment> paymentsJunk = new ArrayList<>(junkSize);

    while (true) {
      Page<Reservation> reservationPage = reservationJpaRepository.findAll(
          PageRequest.of(pageNumber, junkSize)
      );

      List<Reservation> reservations = reservationPage.getContent();

      if (reservations.isEmpty()) {
        break;
      }

      for (Reservation reservation : reservations) {
        if (reservation.getStatus().equals(ReservationStatus.CONFIRMED)) {
          Payment payment = SUT.giveMeBuilder(Payment.class)
              .set("reservationId", reservation.getId())
              .set("version", 0L)
              .build().sample();
          paymentsJunk.add(payment);

          if (paymentsJunk.size() == junkSize) {
            paymentJpaRepository.saveAll(paymentsJunk);
            paymentsJunk.clear();
          }
        }
      }

      pageNumber++;
    }

    if (!paymentsJunk.isEmpty()) {
      paymentJpaRepository.saveAll(paymentsJunk);
    }
  }

}
