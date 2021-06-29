<#import "../partials/main.ftlh" as main />

<@main.renderWith scripts=["/js/validation.js"] styles=["https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"]>
    <h3 class="mt-3">Все TODO</h3>
    <form action="/todo/export" method="post">
        <h3>Поиск был произведен по следующим полям</h3>
        <input name="name" value="${name!''}"><input name="status" value="${status!'all'}"><input name="startDate" type="datetime-local" value="${startDate!''}"><input name="finishDate" type="datetime-local" value="${finishDate!''}">
        <table class="table table-striped">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Дата создания</th>
                <th scope="col">Название</th>
                <th scope="col">Статус</th>
                <th scope="col">Удаление</th>
                <th scope="col">Редактирование</th>
            </tr>
            </thead>
            <tbody>
            <#list todos as todo>

                <tr>
                    <th scope="row">${todo?counter}</th>
                    <td>${todo.date.format('dd-MMM-yyyy')}</td>
                    <td><a href="/todo/open/${todo.id}">${todo.name}</a></td>
                    <td>${todo.status}</td>
                    <td><button onclick="sendFetch()" style="color:red;">Удалить</button></td>
                    <script>
                        async function sendFetch() {
                            var href = window.location.href;
                            await fetch(href+'/delete/'+${todo.id}, {
                                method: 'DELETE',
                            })
                                .then(location.replace(href))
                        }
                    </script>
                    <td><a href="/todo/edit/${todo.id}" style="color:green;">Редактировать</a></td>

                </tr>
            </#list>
            </tbody>
        </table>
        <button type="submit" class="btn btn-primary btn-block">Скачать в Excel</button>
    </form>


</@main.renderWith>