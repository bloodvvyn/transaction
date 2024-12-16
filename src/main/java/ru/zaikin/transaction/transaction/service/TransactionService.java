package ru.zaikin.transaction.transaction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.zaikin.transaction.transaction.models.Order;
import ru.zaikin.transaction.transaction.models.User;
import ru.zaikin.transaction.transaction.repositories.OrderRepository;
import ru.zaikin.transaction.transaction.repositories.UserRepository;

@Service
public class TransactionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    public static void main(String[] args) {

    }

    @Transactional
    public void testTransction() {
        requiredTransaction(); // Будет отработка в рамках одной транзакции
        requiresNewTransaction(); // Отработка всегда будет в двух разных транзакциях
        nestedTransaction(); // отработка будет в единой транзакции, но nested будет зависеть от основной,
                             // если откатится основная транзакция, то и вложенная тоже откатится
                             // но не наоборот
        supportsTransaction(); // Будет выполнен в рамках текущей транзакции, если этот метод вызвать
                                // не как вложенный, то этот метод выполнится без транзакции
        mandatoryTransaction(); // Всегда требует чтобы он был вложенной транзакцией, иначе исключение IllegalTransactionStateException
        neverTransaction(); // всегда должен выполняться без транзакции иначе исключение TransactionRequiredException
        notSupportedTransaction(); // Если выполняется в транзакции, приостанавливает её и выполняется вне транзакции,
        // затем продолжает свою работу


    }

    // REQUIRED: Если транзакция существует, используется она, если нет — создается новая.
    @Transactional(propagation = Propagation.REQUIRED)
    public void requiredTransaction() {
        // выполняем операции с БД
        userRepository.save(new User("Alice"));
        orderRepository.save(new Order("Order1"));
    }

    // REQUIRES_NEW: Всегда создает новую транзакцию.
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void requiresNewTransaction() {
        // выполняем операции с БД
        userRepository.save(new User("Bob"));
        orderRepository.save(new Order("Order2"));
    }

    // NESTED: Создает вложенную транзакцию, которая будет откатана, если основная откатится.
    @Transactional(propagation = Propagation.NESTED)
    public void nestedTransaction() {
        // выполняем операции с БД
        userRepository.save(new User("Charlie"));
        try {
            orderRepository.save(new Order("Order3"));
            // Здесь может быть выброшено исключение, чтобы показать, как работает вложенная транзакция
            throw new RuntimeException("Exception in nested transaction");
        } catch (Exception e) {
            // Вложенная транзакция будет откатана
        }
    }

    // SUPPORTS: Выполнится в рамках существующей транзакции, если она есть.
    @Transactional(propagation = Propagation.SUPPORTS)
    public void supportsTransaction() {
        // выполняем операции с БД
        userRepository.save(new User("David"));
        orderRepository.save(new Order("Order4"));
    }

    // MANDATORY: Требует существующей транзакции, иначе выбрасывает исключение.
    @Transactional(propagation = Propagation.MANDATORY)
    public void mandatoryTransaction() {
        // выполняем операции с БД
        userRepository.save(new User("Eva"));
        orderRepository.save(new Order("Order5"));
    }

    // NEVER: Ожидает выполнение без транзакции, иначе выбрасывает исключение.
    @Transactional(propagation = Propagation.NEVER)
    public void neverTransaction() {
        // выполняем операции с БД
        userRepository.save(new User("Frank"));
        orderRepository.save(new Order("Order6"));
    }

    // NOT_SUPPORTED: Ожидает выполнение без транзакции. Если она есть — приостанавливает.
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void notSupportedTransaction() {
        // выполняем операции с БД
        userRepository.save(new User("Grace"));
        orderRepository.save(new Order("Order7"));
    }

    // Использовал Propagation.REQUIRED для большинства операций, чтобы избежать лишнего создания транзакций
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveMedicalCard() {
        userRepository.save(new User("Patient123"));
    }

    // Propagation.REQUIRES_NEW использовал для логирования ошибок или записи операций,
    // которые не должны зависеть от основной транзакции.
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void logOperation(String message) {
        orderRepository.save(new Order("Log: " + message));
    }

    // Propagation.NESTED применял для сложных операций, например, связанных с обновлением расписания,
    // где часть изменений можно было бы откатить отдельно.
    @Transactional(propagation = Propagation.NESTED)
    public void updateSchedule() {
        try {
            userRepository.save(new User("Doctor123"));
            orderRepository.save(new Order("ScheduleUpdate"));
            // Искусственно выбрасываю исключение для проверки отката
            throw new RuntimeException("Error in nested transaction");
        } catch (Exception e) {
            // Откат только вложенной транзакции
        }
    }

    // Propagation.NOT_SUPPORTED использовал для отчётов или кэша, чтобы не блокировать базу из-за транзакции.
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void generateReport() {
        // Логика работы с данными без транзакции
        System.out.println("Generating report...");
    }
}