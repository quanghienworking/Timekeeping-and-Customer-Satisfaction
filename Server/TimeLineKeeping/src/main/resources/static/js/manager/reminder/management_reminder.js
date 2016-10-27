/**
 * Created by ASUS on 10/24/2016.
 */

var total_pages = 0;
var current_index_page;
var first_page = false;
var last_page = false;

/**
 * Event: click button edit
 * Description: submit form
 */
$('.btn-edit-reminder').on('click', function () {
    var id = $(this).attr('data-id'),
        $form_submit_view_reminder = $('#form-submit-view-reminder'),
        reminderId = $form_submit_view_reminder.find('[name="reminderId"]'),
        $text_time_reminder = $('#text-time-reminder-' + id).val();
    console.info('[id] ' + id);
    console.info('[text time reminder] ' + $text_time_reminder);

    //check (time_reminder - current_time) >= 15 minutes
    var time_reminder = new Date($text_time_reminder);
    var current_time = new Date();
    console.info('[time reminder] ' + time_reminder);
    console.info('[current time] ' + current_time);
    var difference_time = time_reminder - current_time;
    console.info('[difference time] ' + difference_time);
    if (difference_time >= time_out_reminder) {
        reminderId.val(id);

        //submit form
        $form_submit_view_reminder.submit();
    }
});

function load_list_reminders(index) {
    var managerId = $('#text-managerId').val(),
        urlString = "/api/reminder/list_by_manager?managerId=" + managerId +
            '&start=' + index +
            '&top=' + 2,
        $tbody_list_reminders = $('#tbody-list-reminders'),
        content_list_reminders = '';
    $.ajax({
        type: "GET",
        url: urlString,
        success: function (response) {
            var success = response.success;
            console.info('[success] ' + success);
            if (success) {
                var data = response.data,
                    list_reminders = data.content,
                    time_reminder,
                    content_pagination = '',
                    content_list_pages = '',
                    content_previous_page,
                    content_next_page,
                    count_page = 0,
                    $footer_pagination = $('#footer-pagination');
                total_pages = data.totalPages;
                first_page = data.first;
                last_page = data.last;
                console.info('[total pages] ' + total_pages);
                console.info('[first pages] ' + first_page);
                console.info('[last pages] ' + last_page);
                //set current index page: first page
                current_index_page = index;
                //set list reminders
                for (var i = 0; i < list_reminders.length; i++) {
                    time_reminder = new Date(list_reminders[i].time);
                    console.info(time_reminder);
                    content_list_reminders += '<tr>' +
                        '<td>' + list_reminders[i].time + '</td>' +
                        '<td>' + list_reminders[i].title + '</td>' +
                        '<td>' + list_reminders[i].message + '</td>' +
                        '<td>' + list_reminders[i].room.name + '</td>' +
                        '<td>' +
                        '<button class="btn btn-success btn-flat btn-sm btn-edit-reminder" type="button" title="View Reminder">' +
                        '<i class="fa fa-eye"></i>' +
                        '</button>' +
                        ' <button class="btn btn-danger btn-flat btn-sm" type="button" title="Delete Reminder">' +
                        '<i class="fa fa-remove"></i>' +
                        '</button>' +
                        '</td>' +
                        '</tr>';
                }
                //set pagination
                for (var i = 0; i < total_pages; i++) {
                    if (current_index_page == i) {
                        content_list_pages += '<li class="active">';
                    } else {
                        content_list_pages += '<li>';
                    }
                    content_list_pages += '<a onclick="load_list_reminders(' + i + ')">' + (++count_page) + '</a></li>';
                }
                //check if is first page
                if (first_page) {
                    content_previous_page = '<li class="disabled"><a>&laquo;</a></li>';
                } else {
                    content_previous_page = '<li><a>&laquo;</a></li>';
                }
                //check if is last page
                if (last_page) {
                    content_next_page = '<li class="disabled"><a onclick="load_next_page()">&raquo;</a></li>';
                } else {
                    content_next_page = '<li><a onclick="load_next_page()">&raquo;</a></li>';
                }
                content_pagination += '<ul class="pagination pagination-sm no-margin">' +
                    content_previous_page +
                    content_list_pages +
                    content_next_page +
                    '</ul>';
                $tbody_list_reminders.html(content_list_reminders);
                $footer_pagination.html(content_pagination);
            }
        }
    });
}

function load_next_page() {
    //check if is last page
    if (last_page) {
        //do nothing
    } else {
        //current index page + 1
        ++current_index_page;
        //reload list reminders
        load_list_reminders(current_index_page);
    }
}


