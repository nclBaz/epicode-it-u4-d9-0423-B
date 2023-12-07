package riccardogulin;

import com.github.javafaker.Faker;
import riccardogulin.entities.User;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Application {

	public static void main(String[] args) {

		Supplier<Integer> integerSupplier = () -> {
			Random rndm = new Random();
			return rndm.nextInt(1, 101);
		};


		Supplier<User> userSupplier = () -> {
			Faker faker = new Faker(Locale.ITALY);
			return new User(faker.lordOfTheRings().character(), faker.name().lastName(), integerSupplier.get(), faker.gameOfThrones().city());
		};

		List<User> users = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			users.add(userSupplier.get());
		}

		System.out.println("La lista di utenti ha " + users.size() + " elementi");

		users.forEach(System.out::println);

		int totaleEtà = users.stream().filter(user -> user.getAge() < 18).map(User::getAge).reduce(0, (partialSum, currentElem) -> partialSum + currentElem);
		System.out.println("Totale: " + totaleEtà);


		// ********************************************************* COLLECTORS ********************************************************
		System.out.println("********************************************************* COLLECTORS ********************************************************");
		// 1. Raggruppare gli users per città
		Map<String, List<User>> usersPerCittà = users.stream().filter(user -> user.getAge() < 18).collect(Collectors.groupingBy(user -> user.getCity()));
		usersPerCittà.forEach((città, listaUtenti) -> System.out.println("Città " + città + ", " + listaUtenti));

		// 2. Raggruppare gli users per età
		Map<Integer, List<User>> usersPerEtà = users.stream().filter(user -> user.getAge() < 18).collect(Collectors.groupingBy(user -> user.getAge()));
		usersPerEtà.forEach((età, listaUtenti) -> System.out.println("Età " + età + ", " + listaUtenti));

		// 3. Concatenare tutti i nomi degli user ottenendo una stringa tipo nome cognome, nome cognome, ...
		String nomiConcatenati = users.stream().map(user -> user.getName() + " " + user.getSurname()).collect(Collectors.joining(", "));
		System.out.println(nomiConcatenati);

		// 4. Calcolare la media delle età
		double average = users.stream().collect(Collectors.averagingInt(user -> user.getAge()));
		System.out.println("Media delle età: " + average);

		// 5. Calcolare la media delle età raggruppando per città
		Map<String, Double> mediaEtàPerCittà = users.stream().collect(Collectors.groupingBy(User::getCity, Collectors.averagingInt(User::getAge)));
		mediaEtàPerCittà.forEach((città, mediaEtà) -> System.out.println("Città: " + città + ", " + mediaEtà));

		// 6. Raggruppare per città con informazioni su età minima, massima, somma delle età, media delle età e conteggio utenti di quella città
		Map<String, IntSummaryStatistics> mediaEtàPerCittàConStatistiche = users.stream().collect(Collectors.groupingBy(User::getCity, Collectors.summarizingInt(User::getAge)));
		mediaEtàPerCittàConStatistiche.forEach((città, stats) -> System.out.println("Città: " + città + ", " + stats));

		// ********************************************************* COMPARATORS ********************************************************
		System.out.println("********************************************************* COMPARATORS ********************************************************");
		// A differenza dei metodi per ordinare di Collection, i quali modificano la lista originale ordinandola, i Comparator ci consento di restituire una NUOVA LISTA ORDINATA

		// 1. Ordinare in base all'età (ordine crescente)
		List<User> usersSortedByAge = users.stream().sorted(Comparator.comparingInt(User::getAge)).toList();
		usersSortedByAge.forEach(System.out::println);

		// 2. Ordinare in base all'età (ordine decrescente)
		List<User> usersSortedByAgeDesc = users.stream().sorted(Comparator.comparingInt(User::getAge).reversed()).toList();
		//List<User> usersSortedByAgeDesc = users.stream().sorted(Comparator.reverseOrder()).toList();
		usersSortedByAgeDesc.forEach(System.out::println);

		// 3. Ordinare in base al nome (ordine crescente)
		List<User> usersSortedByName = users.stream().sorted(Comparator.comparing(User::getName)).toList();
		usersSortedByName.forEach(System.out::println);

		// 4. Ordinare in base al nome (ordine decrescente)
		List<User> usersSortedByNameDesc = users.stream().sorted(Comparator.comparing(User::getName, Comparator.reverseOrder())).toList();
		usersSortedByNameDesc.forEach(System.out::println);

	}
}
