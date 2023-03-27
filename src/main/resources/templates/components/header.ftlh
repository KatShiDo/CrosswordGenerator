<header>
    <a href="#" id="header-link">СОЗДАТЬ КРОССВОРД</a>
    <form action="" method="post" id="crossword-find-form">
        <input type="text" name="search-crossword" id="search-crossword" placeholder="Найти кроссворд">
        <button type="submit" id="search-submit-button"><img src="../static/search.png" alt=""></button>
    </form>
    <div class="account-buttons-holder">
        <button type="button" class="account-button" id="sign-in">Войти</button>
        <button type="button" class="account-button" id="sign-up">Регистрация</button>
    </div>
    <div class="sign-form signin-form">
        <span class="sing-title">Войти</span>
        <input type="text" name="signin-email" id="signin-email" class="account-info-field" required>
        <label for="signin-email" class="account-label" id="signin-email-label">Email или имя пользователя</label>
        <input type="password" name="signin-psw" id="signin-psw" class="account-info-field" autocomplete="current-password" required>
        <label for="signin-psw" class="account-label" id="psw-label">Пароль</label>
        <button type="button" class="sign-button highlighted-button" id="signin-button">Войти</button>
        <button type="button" class="close-form" id="close-signin-form"><img src="../static/cross-23.svg" height="20px" width="20px" class="green-cross"></button>
    </div>
    <div class="sign-form signup-form">
        <span class="sing-title">Регистрация</span>
        <input type="text" name="signup-email" id="signup-email" class="account-info-field" required>
        <label for="signup-email" class="account-label" id="signup-email-label">Email или имя пользователя</label>
        <input type="password" name="signup-psw1" id="signup-psw1" class="account-info-field" required>
        <label for="signup-psw1" class="account-label" id="psw-label1">Пароль</label>
        <input type="password" name="signup-psw2" id="signup-psw2" class="account-info-field" required>
        <label for="signup-psw2" class="account-label" id="psw-label2">Подтвердите пароль</label>
        <button type="button" class="sign-button highlighted-button" id="signup-button">Регистрация</button>
        <button type="button" class="close-form" id="close-signup-form"><img src="../static/cross-23.svg" height="20px" width="20px" class="green-cross"></button>
    </div>
</header>