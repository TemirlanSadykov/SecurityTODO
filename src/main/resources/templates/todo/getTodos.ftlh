<#import "../partials/main.ftlh" as main />

<@main.renderWith scripts=["/js/validation.js"] styles=["https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"]>
    <h3 class="mt-3">Все TODO</h3>
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
        <#list items as todo>

                    <tr>
                        <th scope="row">${todo?counter}</th>
                        <td>${todo.date.format('dd-MMM-yyyy')}</td>
                        <td><a href="/todo/open/${todo.id}">${todo.name}</a></td>
                        <td>${todo.status}</td>
                        <td><button onclick="sendFetch${todo.id?string}()" style="color:red;">Удалить</button></td>
                            <script>
                             function sendFetch${todo.id?string}() {
                                var href = window.location.href;
                                 fetch(href+'/delete/'+${todo.id?string}, {
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
    <#include "../partials/pagination.ftlh">

</@main.renderWith>