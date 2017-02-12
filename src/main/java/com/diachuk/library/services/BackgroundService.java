package com.diachuk.library.services;

import com.diachuk.library.dao.entities.BookLoan;
import com.diachuk.library.dao.implementations.MySql.*;
import com.diachuk.library.manage.LibraryConfig;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.concurrent.TimeUnit.MINUTES;

/**
 * Created by VA-N_ on 30.01.2017.
 */
public class BackgroundService {

    private static boolean runningStatus;
    private static BackgroundService instance;
    private final ScheduledExecutorService scheduler;
    private static ArrayList<ScheduledFuture<?>> backgroundTasks;

    private BackgroundService() {
        scheduler = Executors.newScheduledThreadPool(5);
    }

    private void assignBackgroundTasks() {
        backgroundTasks = new ArrayList<>();
        backgroundTasks.add(scheduler.scheduleAtFixedRate(rRoomReservationsClean, 5, 5, MINUTES));
        backgroundTasks.add(scheduler.scheduleAtFixedRate(homeReservationsClean, 1, 1, DAYS));
        backgroundTasks.add(scheduler.scheduleAtFixedRate(oldQuestionsClean, 1, 30, DAYS));
        backgroundTasks.add(scheduler.scheduleAtFixedRate(oldNewsClean, 1, 30, DAYS));
        backgroundTasks.add(scheduler.scheduleAtFixedRate(overdueMailDistribution, 1, 2, DAYS));
    }

    public static boolean isRunningStatus() {
        return runningStatus;
    }

    public static BackgroundService getInstance() {
        if (instance == null) {
            instance = new BackgroundService();
        }
        return instance;
    }

    private final Runnable rRoomReservationsClean = new Runnable() {
        public void run() {
            try {
                MySqlRRoomReservationDAO.getInstance().deleteOverdue();
            } catch (SQLException e) {
                // TODO: 31.01.2017 logging
                e.printStackTrace();
            }
        }
    };

    private final Runnable homeReservationsClean = new Runnable() {
        public void run() {
            try {
                MySqlHomeReservationDAO.getInstance().deleteOverdue();
            } catch (SQLException e) {
                // TODO: 31.01.2017 logging
                e.printStackTrace();
            }
        }
    };

    private final Runnable oldQuestionsClean = new Runnable() {
        public void run() {
            try {
                MySqlQuestionDAO.getInstance().deleteOlderThan(LibraryConfig.getInstance().getQuestionLifeTime());
            } catch (SQLException e) {
                // TODO: 31.01.2017 logging
                e.printStackTrace();
            }
        }
    };

    private final Runnable oldNewsClean = new Runnable() {
        public void run() {
            try {
                MySqlNewsPostDAO.getInstance().deleteOlderThan(LibraryConfig.getInstance().getNewsLifeTime());
            } catch (SQLException e) {
                // TODO: 31.01.2017 logging
                e.printStackTrace();
            }
        }
    };

    private final Runnable overdueMailDistribution = new Runnable() {
        public void run() {
            ArrayList<BookLoan> overdueBookLoans = null;
            try {
                overdueBookLoans = MySqlCrossTableDAO.getInstance().findNotReturnedOverdueBookLoans();
                if (overdueBookLoans.isEmpty()) {
                    return;
                }
                EmailService emailService = new EmailService();
                for (BookLoan bookLoan : overdueBookLoans) {
                    emailService.sendOverdueNotification(bookLoan);
                }
            } catch (SQLException e) {
                // TODO: 01.02.2017 logging
                e.printStackTrace();
            }
        }
    };

    public void RestartBackgroundProcesses() {
        stopBackgroundProcesses();
        startBackgroundProcesses();
        runningStatus = true;
    }

    public void stopBackgroundProcesses() {
        for (ScheduledFuture<?> task : backgroundTasks) {
            task.cancel(false);
        }
        runningStatus = false;
    }

    public void startBackgroundProcesses() {
        if (!runningStatus) {
            assignBackgroundTasks();
            runningStatus = true;
        }
    }

    public void shutdown() {
        for (ScheduledFuture<?> task : backgroundTasks) {
            task.cancel(false);
        }
        try {
            Thread.sleep(1000);
            scheduler.shutdown();
        } catch (InterruptedException e) {
            System.out.println("Background stop ERROR");
            // TODO: 02.02.2017 loging
        }
    }

}
