# 📘 Programowanie Obiektowe – Projekt indywidualny

## 🏷️ Strona tytułowa

**Kurs:** Programowanie Obiektowe  
**Tytuł projektu:** VaultCore – System bankowy  
**Autor:** Kamil Kalicki

---

## 📄 Opis zadania

Celem projektu było zaprojektowanie i zaimplementowanie systemu bankowego w języku Java zgodnie z zasadami programowania obiektowego.

### Założenia:
- obsługa użytkowników (klientów banku),
- obsługa kont bankowych różnych typów,
- wykonywanie operacji finansowych,
- rejestrowanie historii transakcji,
- zapis i odczyt danych do/z pliku,
- graficzny interfejs użytkownika (Swing).

### Zakres funkcjonalności:
- tworzenie użytkowników,
- tworzenie kont:
    - konto osobiste (CheckingAccount),
    - konto oszczędnościowe (SavingAccount),
    - konto firmowe (BusinessAccount),
- operacje:
    - wpłata (deposit),
    - wypłata (withdraw),
    - przelew (transfer),
- naliczanie miesięcznych aktualizacji (`monthly_update`),
- historia transakcji,
- trwałość danych (serializacja do pliku).

### Ograniczenia:
- uproszczona walidacja email,
- brak integracji z bazą danych,
- uproszczony interfejs graficzny.

---

## 🖥️ Instrukcja użytkownika

1. Uruchom aplikację (`Main.java`).
2. 2. Zaloguj się poprawnymi danymi.
4. W zakładce **Clients**:
    - wprowadź dane użytkownika,
    - kliknij **Add user**.
5. W zakładce **Accounts**:
    - wybierz użytkownika,
    - wybierz typ konta,
    - (opcjonalnie) ustaw oprocentowanie,
    - kliknij **Create account**.
6. W zakładce **Operations**:
    - wykonuj operacje:
        - wpłata,
        - wypłata,
        - przelew,
        - aktualizacja miesięczna.
7. W zakładce **Transactions**:
    - przeglądaj historię operacji.
8. Dane zapisywane są automatycznie po każdej operacji.

---

## ⚙️ Najważniejsze funkcjonalności

### 🔹 Model domenowy
System oparty na klasach:
- `User`
- `BankAccount`
- `Transaction`

Zastosowano enkapsulację oraz podział odpowiedzialności.

---

### 🔹 Dziedziczenie i polimorfizm

```java
public abstract void monthly_update();
```

Każdy typ konta implementuje własne zachowanie.

---

### 🔹 Warstwa serwisowa

`BankService` zarządza logiką aplikacji:
- operacje finansowe,
- zarządzanie użytkownikami i kontami,
- rejestrowanie transakcji.

---

### 🔹 Obsługa wyjątków

- `InvalidAmountException`
- `InsufficientFundsException`
- `InvalidEmailException`
- `AccountNotFoundException`

---

### 🔹 Trwałość danych

Dane zapisywane są do pliku przy użyciu serializacji:

```java
storage.save(...)
storage.load(...)
```

Stan aplikacji jest zachowany między uruchomieniami.

---

### 🔹 GUI (Swing)

Aplikacja posiada interfejs graficzny umożliwiający:
- dodawanie użytkowników,
- tworzenie kont,
- wykonywanie operacji,
- przegląd historii.

---

## 📸 Zrzuty ekranu

*(do uzupełnienia)*

---

## 🧩 Podsumowanie

Projekt spełnia wymagania:
- wykorzystuje zasady OOP,
- zawiera dziedziczenie i polimorfizm,
- posiada GUI,
- obsługuje wyjątki,
- zapisuje dane do pliku.

Aplikacja została zaprojektowana w sposób umożliwiający dalszą rozbudowę.
