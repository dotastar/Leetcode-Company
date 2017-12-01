package general.misc;

import java.util.Optional;
import lombok.Data;


@Data
public class OptionalExample {

  @SuppressWarnings("unused")
  public static void main(String[] args) {
    Optional<Computer> computerOptional = Optional.ofNullable(buildComputer());
    String version = computerOptional
        .flatMap(Computer::getSoundCard)
        .flatMap(SoundCard::getUsb)
        .map(USB::getVersion)
        .orElse("UNKNOWN");
    System.out.println("Current version is " + version);

    /**
     * Optional::map() vs Optional::flatMap()
     */
    Optional<Optional<SoundCard>> soundCard1 = computerOptional.map(Computer::getSoundCard);
    Optional<SoundCard> soundCard2 = computerOptional.flatMap(Computer::getSoundCard);
  }

  private static Computer buildComputer() {
    Computer computer = new Computer();
    computer.soundCard = Optional.of(new SoundCard());
    computer.soundCard.get().setUsb(Optional.empty());
    return computer;
  }

  @Data
  private static class Computer {
    private Optional<SoundCard> soundCard;
  }

  @Data
  static class SoundCard {
    private Optional<USB> usb;
  }

  @Data
  static class USB {
    private String version;
  }
}
