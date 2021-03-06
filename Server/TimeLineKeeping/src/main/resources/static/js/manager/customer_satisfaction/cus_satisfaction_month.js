/**
 * Created by TrungNN on 10/5/2016.
 */

//Date picker
$('#date-picker-cs-month').datepicker({
    format: com_full_year_month,
    viewMode: "months",
    minViewMode: "months",
    endDate: new Date(),
    autoclose: true
});

/**
 * Event: change to tag date
 */
$('#change-to-tag-date').on('click', function () {
    var $form_submit_change_to_tag_date = $('#form-submit-change-to-tag-date');

    // submit form
    $form_submit_change_to_tag_date.submit();
});

/**
 * Event: select another month
 */
$('.selected-date').on('change', function () {
    var selectedDate = $('.selected-date').val(),
        $form_submit_change_month = $('#form-submit-change-month'),
        selectedMonth = $form_submit_change_month.find('[name="selectedMonth"]');
    selectedMonth.val(selectedDate);

    // submit form
    $form_submit_change_month.submit();
});

/**
 * Submit form view details
 */
$('.view-details-customer-service').on('click', function () {
    var id = $(this).attr('data-id'),
        $form_submit_details = $('#form-submit-view-details-' + id),
        accountCustomerServiceDetails = $form_submit_details.find('[name="accountCustomerServiceDetails"]'),
        accountId = id,
        selectedDate = $('.selected-date').val(),
        accountCustomerServiceDetailsJson = '{';
    accountCustomerServiceDetailsJson += '"accountId":' + accountId;
    accountCustomerServiceDetailsJson += ', "selectedDate":"' + selectedDate + '"';
    accountCustomerServiceDetailsJson += '}';

    accountCustomerServiceDetails.val(accountCustomerServiceDetailsJson);

    // submit form
    $form_submit_details.submit();
});