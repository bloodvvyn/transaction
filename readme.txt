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