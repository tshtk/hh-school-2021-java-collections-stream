package tasks;

import common.Person;
import common.Task;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
А теперь о горьком
Всем придется читать код
А некоторым придется читать код, написанный мною
Сочувствую им
Спасите будущих жертв, и исправьте здесь все, что вам не по душе!
P.S. функции тут разные и рабочие (наверное), но вот их понятность и эффективность страдает (аж пришлось писать комменты)
P.P.S Здесь ваши правки желательно прокомментировать (можно на гитхабе в пулл реквесте)
 */
public class Task8 implements Task {

  private long count;

  /*Не хотим выдавать апи нашу фальшивую персону, поэтому конвертим начиная со второй

  Заменил проверку списка на отсутствие элементов через метод isEmpty()
  Начиная с Java 16, для создания неизменяемого списка из потока используется: Stream.toList().
  Пропуск первого элемента реализовал в стриме.
   */

  public List<String> getNames(List<Person> persons) {
    if (persons.isEmpty()) {
      return Collections.emptyList();
    }
    return persons.stream().skip(1).map(Person::getFirstName).toList();
  }

  /* ну и различные имена тоже хочется
  Не вижу смысла в применении distinct, если следом идет сборка в Set
  Да и вообще, не стоит тут stream использовать
  Переименовал метод, чтобы понятнее было, что он делает
   */

  public Set<String> getUniqueNames(List<Person> persons) {
    return new HashSet<>(getNames(persons));
  }

  /*Для фронтов выдадим полное имя, а то сами не могут

  Переименовал метод, чтобы было понятнее, что он делает.
  В исходном варианте фамилия добавлялась в полное имя дважды - думаю, что баг, исправил.
   */
  public static String getFullName(Person person) {
    return Stream.of(person.getFirstName(), person.getSecondName())
            .filter(Objects::nonNull).collect(Collectors.joining(" "));
  }

  /* словарь id персоны -> ее имя
  Тут была логика, что если id повторяется, то не записывать в Map, я бы спросил у разработчика, что он имел ввиду,
  и если эта логика здесь не необходима то переписал бы метод через стрим, для этого сделал статичным метод getFullName
  Переименовал метод, чтобы было понятнее, что он делает.

   */

  public Map<Integer, String> getFullNames(Collection<Person> persons) {
    return persons.stream().collect(Collectors.toMap(Person::getId, Task8::getFullName, (a1, a2) -> a1));
  }

  /* есть ли совпадающие в двух коллекциях персоны?
  Можно использовать метод Collection retainAll
   */

  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    Set<Person> intersection = new HashSet<>(persons1);
    intersection.retainAll(persons2);
    return !intersection.isEmpty();
  }

  /*...
  Этот метод будто лишний здесь, как и объявление переменной count в классе, а не в теле метода

   */
  public long countEven(Stream<Integer> numbers) {
    count = 0;
    numbers.filter(num -> num % 2 == 0).forEach(num -> count++);
    return count;
  }

  @Override
  public boolean check() {
    //System.out.println("Слабо дойти до сюда и исправить Fail этой таски?");
    boolean codeSmellsGood = true;
    boolean reviewerDrunk = false;
    return codeSmellsGood || reviewerDrunk;
  }
}
