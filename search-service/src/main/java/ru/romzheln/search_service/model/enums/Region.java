package ru.romzheln.search_service.model.enums;

import lombok.Getter;
import ru.romzheln.search_service.exception.UnknownRegionException;

import java.util.HashMap;
import java.util.Map;

public enum Region {
  РЕСПУБЛИКА_АДЫГЕЯ(1, "adygea"),
  РЕСПУБЛИКА_БАШКОРТОСТАН(2, "bashkortostan"),
  РЕСПУБЛИКА_БУРЯТИЯ(3, "buryatia"),
  РЕСПУБЛИКА_АЛТАЙ_ГОРНЫЙ_АЛТАЙ(4, "altai"),
  РЕСПУБЛИКА_ДАГЕСТАН(5, "dagestan"),
  РЕСПУБЛИКА_ИНГУШЕТИЯ(6, "ingushetia"),
  КАБАРДИНО_БАЛКАРСКАЯ_РЕСПУБЛИКА(7, "kabardino-balkarian"),
  РЕСПУБЛИКА_КАЛМЫКИЯ(8, "kalmykia"),
  РЕСПУБЛИКА_КАРАЧАЕВО_ЧЕРКЕССИЯ(9, "karachay-cherkessia"),
  РЕСПУБЛИКА_КАРЕЛИЯ(10, "karelia"),
  РЕСПУБЛИКА_КОМИ(11, "komi"),
  РЕСПУБЛИКА_МАРИЙ_ЭЛ(12, "mari-el"),
  РЕСПУБЛИКА_МОРДОВИЯ(13, "mordovia"),
  РЕСПУБЛИКА_САХА_ЯКУТИЯ(14, "sakha"),
  РЕСПУБЛИКА_СЕВЕРНАЯ_ОСЕТИЯ_АЛАНИЯ(15, "north-ossetia"),
  РЕСПУБЛИКА_ТАТАРСТАН(16, "tatarstan"),
  РЕСПУБЛИКА_ТЫВА(17, "tyva"),
  УДМУРТСКАЯ_РЕСПУБЛИКА(18, "udmurt"),
  РЕСПУБЛИКА_ХАКАСИЯ(19, "khakassia"),
  ЧУВАШСКАЯ_РЕСПУБЛИКА(21, "chuvashia"),
  АЛТАЙСКИЙ_КРАЙ(22, "altaisky"),
  КРАСНОДАРСКИЙ_КРАЙ(23, "krasnodar"),
  КРАСНОЯРСКИЙ_КРАЙ(24, "krasnoyarsk"),
  ПРИМОРСКИЙ_КРАЙ(25, "primorye"),
  СТАВРОПОЛЬСКИЙ_КРАЙ(26, "stavropol"),
  ХАБАРОВСКИЙ_КРАЙ(27, "khabarovsk"),
  АМУРСКАЯ_ОБЛАСТЬ(28, "amur"),
  АРХАНГЕЛЬСКАЯ_ОБЛАСТЬ(29, "arkhangelsk"),
  АСТРАХАНСКАЯ_ОБЛАСТЬ(30, "astrakhan"),
  БЕЛГОРОДСКАЯ_ОБЛАСТЬ(31, "belgorod"),
  БРЯНСКАЯ_ОБЛАСТЬ(32, "bryansk"),
  ВЛАДИМИРСКАЯ_ОБЛАСТЬ(33, "vladimir"),
  ВОЛГОГРАДСКАЯ_ОБЛАСТЬ(34, "volgograd"),
  ВОЛОГОДСКАЯ_ОБЛАСТЬ(35, "vologda"),
  ВОРОНЕЖСКАЯ_ОБЛАСТЬ(36, "voronezh"),
  ИВАНОВСКАЯ_ОБЛАСТЬ(37, "ivanovo"),
  ИРКУТСКАЯ_ОБЛАСТЬ(38, "irkutsk"),
  КАЛИНИНГРАДСКАЯ_ОБЛАСТЬ(39, "kaliningrad"),
  КАЛУЖСКАЯ_ОБЛАСТЬ(40, "kaluga"),
  КАМЧАТСКИЙ_КРАЙ(41, "kamchatka"),
  КЕМЕРОВСКАЯ_ОБЛАСТЬ(42, "kemerovo"),
  КИРОВСКАЯ_ОБЛАСТЬ(43, "kirov"),
  КОСТРОМСКАЯ_ОБЛАСТЬ(44, "kostroma"),
  КУРГАНСКАЯ_ОБЛАСТЬ(45, "kurgan"),
  КУРСКАЯ_ОБЛАСТЬ(46, "kursk"),
  ЛЕНИНГРАДСКАЯ_ОБЛАСТЬ(47, "leningrad"),
  ЛИПЕЦКАЯ_ОБЛАСТЬ(48, "lipetsk"),
  МАГАДАНСКАЯ_ОБЛАСТЬ(49, "magadan"),
  МОСКОВСКАЯ_ОБЛАСТЬ(50, "moscow-region"),
  МУРМАНСКАЯ_ОБЛАСТЬ(51, "murmansk"),
  НИЖЕГОРОДСКАЯ_ОБЛАСТЬ(52, "nizhny-novgorod"),
  НОВГОРОДСКАЯ_ОБЛАСТЬ(53, "novgorod"),
  НОВОСИБИРСКАЯ_ОБЛАСТЬ(54, "novosibirsk"),
  ОМСКАЯ_ОБЛАСТЬ(55, "omsk"),
  ОРЕНБУРГСКАЯ_ОБЛАСТЬ(56, "orenburg"),
  ОРЛОВСКАЯ_ОБЛАСТЬ(57, "orel"),
  ПЕНЗЕНСКАЯ_ОБЛАСТЬ(58, "penza"),
  ПЕРМСКИЙ_КРАЙ(59, "perm"),
  ПСКОВСКАЯ_ОБЛАСТЬ(60, "pskov"),
  РОСТОВСКАЯ_ОБЛАСТЬ(61, "rostov"),
  РЯЗАНСКАЯ_ОБЛАСТЬ(62, "ryazan"),
  САМАРСКАЯ_ОБЛАСТЬ(63, "samara"),
  САРАТОВСКАЯ_ОБЛАСТЬ(64, "saratov"),
  САХАЛИНСКАЯ_ОБЛАСТЬ(65, "sakhalin"),
  СВЕРДЛОВСКАЯ_ОБЛАСТЬ(66, "sverdlovsk"),
  СМОЛЕНСКАЯ_ОБЛАСТЬ(67, "smolensk"),
  ТАМБОВСКАЯ_ОБЛАСТЬ(68, "tambov"),
  ТВЕРСКАЯ_ОБЛАСТЬ(69, "tver"),
  ТОМСКАЯ_ОБЛАСТЬ(70, "tomsk"),
  ТУЛЬСКАЯ_ОБЛАСТЬ(71, "tula"),
  ТЮМЕНСКАЯ_ОБЛАСТЬ(72, "tyumen"),
  УЛЬЯНОВСКАЯ_ОБЛАСТЬ(73, "ulyanovsk"),
  ЧЕЛЯБИНСКАЯ_ОБЛАСТЬ(74, "chelyabinsk"),
  ЗАБАЙКАЛЬСКИЙ_КРАЙ(75, "zabaykalsky"),
  ЯРОСЛАВСКАЯ_ОБЛАСТЬ(76, "yaroslavl"),
  ГОРОД_МОСКВА(77, "moscow"),
  ГОРОД_САНКТ_ПЕТЕРБУРГ(78, "saint-petersburg"),
  ЕВРЕЙСКАЯ_АВТОНОМНАЯ_ОБЛАСТЬ(79, "eao"),
  РЕСПУБЛИКА_КРЫМ(82, "krym"),
  НЕНЕЦКИЙ_АВТОНОМНЫЙ_ОКРУГ(83, "nenets"),
  ХАНТЫ_МАНСИЙСКИЙ_АВТОНОМНЫЙ_ОКРУГ_ЮГРА(86, "yugra"),
  ЧУКОТСКИЙ_АВТОНОМНЫЙ_ОКРУГ(87, "chukotka"),
  ЯМАЛО_НЕНЕЦКИЙ_АВТОНОМНЫЙ_ОКРУГ(89, "yamal-nenets"),
  ГОРОД_СЕВАСТОПОЛЬ(92, "sevastopol"),
  ЧЕЧЕНСКАЯ_РЕСПУБЛИКА(95, "chechnya");

  @Getter
  private final int index;
  @Getter
  private final String url;

  Region(int index, String url) {
    this.index = index;
    this.url = url;
  }

    private static final Map<Integer, Region> INDEX_MAP = new HashMap<>();
  private static final Map<String, Region> URL_REGION = new HashMap<>();

  static {
    for (Region region : Region.values()) {
      INDEX_MAP.put(region.getIndex(), region);
    }
  }

  static {
      for(Region region : Region.values()){
          URL_REGION.put(region.url, region);
      }
  }

  public static Region getByIndex(int index) {
    return INDEX_MAP.get(index);
  }

  public static Region getRegionByUrl(String url){
      Region region = URL_REGION.get(url);
      if (region == null) {
          throw new UnknownRegionException(url);
      }
      return region;
  }
}
