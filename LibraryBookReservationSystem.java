public class LibraryBookReservationSystem {

    static class Library {
        private int availableCopies;

        public Library(int availableCopies) {
            this.availableCopies = availableCopies;
        }

        public synchronized boolean reserveBook() {
            if (availableCopies > 0) {
                availableCopies--;
                System.out.println(Thread.currentThread().getName() + " reserved a book. Copies remaining: " + availableCopies);
                return true;
            } else {
                System.out.println(Thread.currentThread().getName() + " could not reserve a book. No copies available.");
                return false;
            }
        }

        public int getAvailableCopies() {
            return availableCopies;
        }
    }

    static class UserThread extends Thread {
        private Library library;

        public UserThread(Library library) {
            this.library = library;
        }

        @Override
        public void run() {
            while (true) {
                boolean success = library.reserveBook();
                if (success) {
                    break;
                } else {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Library library = new Library(5);

        for (int i = 0; i < 10; i++) {
            UserThread userThread = new UserThread(library);
            userThread.setName("User " + (i + 1));
            userThread.start();
        }
    }
}