# DistanceTraveled
###### Spring Boot project

## Step-by-step tutorial "How to install and launch Neo4j database server"

*Пошаговый туториал "Как уствновить и запустить сервер Neo4j базы даных"*


1. [Here ](https://neo4j.com/download-center/#releases) is official distributives. I suggest we won't need Enterprise edition, so download last version of Community Server (Neo4j 3.4.5 (zip) in my case).

*По ссылке выше лежат оффициальные дистрибутивы. Скачайте последнюю версию Community Server (Neo4j 3.4.5 (zip)), 
Enterprise нам не понадобится)*

2. Then extract the folder into your file system (for example, to  'C:\Program Files\neo4j-community-3.4.5' directory)

*Распакуйте архив, например в папку 'Program Files'.*

3. Congratulations, your database is almost ready!  To start working open a console as administrator and navigate to the \bin directory of your server. (cmd,  cd C:\Program Files\neo4j-community-3.4.5\bin)

*Поздравляю, ваша база данных почти готова! Для запуса сервера необходимо открыть консоль от имени администратора и перейти в папку \bin  вашего сервера.*

4. Execute 'neo4j console' command. You should see the output like that
 

*Запустите команду  'neo4j console'.  Вы должны увидеть примерно такой результат. (картинка не прикрепляется:с)*

5. Next, to activate your database account open http://localhost:7474/  . It's default database interface where server is deployed. 

*Далее, что бы активировать учетную запись пользователя переходим по адресу.
Это дефолтный адрес удаленного доступа к базе данных.*

6. Default settings provides user 'neo4j'  and password 'neo4j'.  After authentification you would be offered to change the password. I sugges  you to choose 'NCEdu' =) However it may be any combination. 

*Дефолтные настройки дают пользователя с именем/паролем neo4j/neo4j. После входа в систему вам предложат сменить пароль. Предлагаю выбрать 'NCEdu'. 
Однако пароль может быть любым.*

7. If you choose password different to 'NCEdu' you should change application.propeties file in your project to maintain Spring Boot configuration

*Если вы выбрали отличный от 'NCEdu' пароль, то необходимо изминить файл application.propeties в вашем проекте,
иначе Spring Boot не сможет поддерживать подключение.*
