/**
 * Created by TrungNN on 10/4/2016.
 */

var accountId = $('#accountId').val();
var customerCode;
var timer_get_emotion;

/**
 * Event: next transaction
 */
$('#btn-next-transaction').on('click', function () {
    //disable button next
    event_disabled('#btn-next-transaction', true);
    //disable button skip
    event_disabled('#btn-skip-transaction', true);
    //hide div overview customer emotion
    event_hide('#div-overview-customer-emotion');
    //show div loader
    event_show('#div-loader');

    //call request: next transaction
    worker_next_transaction();
});

/**
 * Event: skip transaction
 */
$('#btn-skip-transaction').on('click', function () {
    console.info('Running btn skip');
    //disable button next
    event_disabled('#btn-next-transaction', true);
    //disable button skip
    event_disabled('#btn-skip-transaction', true);
    //hide div overview customer emotion
    event_hide('#div-overview-customer-emotion');
    //show div loader
    event_show('#div-loader');

    //call request: get first emotion
    worker_get_emotion();
});

/**
 * Worker: get first emotion
 * Description: run on loading page or clicking skip button
 */
function worker_get_emotion() {
    var urlString = '/api/emotion/get_emotion?accountId=' + accountId;
    console.info('[worker_get_emotion] accountId: ' + accountId);
    $.ajax({
        type: "GET",
        url: urlString,
        // data: formDataJson,
        success: function (response) {
            console.info('success: ' + response.success);
            if (response.success) {
                var data = response.data,
                    messages = data.messages,
                    customer_emotion_msg = messages.message,
                    suggestions = messages.sugguest,
                    age_predict = messages.predict,
                    gender = messages.gender,
                    urlImage = messages.url,
                    imageByte = messages.image,
                    $font_age_predict = $('#font-age-predict'),
                    $font_gender = $('#font-gender'),
                    $customer_emotion_msg = $('#customer-emotion-message'),
                    $suggestion_behavior_msg = $('#suggestion-behavior-message'),
                    ul_content_customer_emotion = '',
                    ul_content_suggestion_behavior = '';

                //hide div loader
                event_hide('#div-loader');
                //show div overview customer emotion
                event_show('#div-overview-customer-emotion');
                //enable button next
                event_disabled('#btn-next-transaction', false);
                //enable button skip
                event_disabled('#btn-skip-transaction', false);

                //set age predict
                if (age_predict != null) {
                    $font_age_predict.html(age_predict);
                } else {
                    $font_age_predict.html('N/A');
                }

                //set gender
                if (gender == 0) {
                    $font_gender.html('Nam');
                } else {
                    $font_gender.html('Nữ');
                }

                //set image customer
                if (urlImage != null) {
                    // setSrcImage('#image-customer', urlImage)
                    setSrcImage('#image-customer', "data:image/png;base64," + imageByte)
                } else {
                    setSrcImage('#image-customer', '/libs/dist/img/avatar_customer.png')
                }

                //set customer emotion message
                if (customer_emotion_msg != null && customer_emotion_msg.length > 0) {
                    for (var i = 0; i < customer_emotion_msg.length; i++) {
                        ul_content_customer_emotion += '<li>' +
                            customer_emotion_msg[i] +
                            '</li>';
                    }
                } else {
                    ul_content_customer_emotion += '<li>N/A</li>';
                }
                $customer_emotion_msg.html(ul_content_customer_emotion);

                //set suggestion behavior
                if (suggestions != null && suggestions.length > 0) {
                    for (var i = 0; i < suggestions.length; i++) {
                        ul_content_suggestion_behavior += '<li>' +
                            suggestions[i] +
                            '</li>';
                    }
                } else {
                    ul_content_suggestion_behavior += '<li>N/A</li>';
                }
                $suggestion_behavior_msg.html(ul_content_suggestion_behavior);

                //stop request: get first emotion
                clearTimeout(timer_get_emotion);
            }
        }
    });
    timer_get_emotion = setTimeout(worker_get_emotion, time_out);
};

/**
 * Worker: next transaction
 * Description: ending current transaction and creating new transaction
 */
function worker_next_transaction() {
    var urlString = '/api/emotion/next?accountId=' + accountId;
    $.ajax({
        type: "GET",
        url: urlString,
        // data: formDataJson,
        success: function (response) {
            if (response.success) {
                customerCode = response.data;
                if (customerCode != null) {
                    //call request: get first emotion
                    worker_get_emotion();
                }
            } else {
                alert(response.data);
            }
        }
    });
};

/**
 * Event: disabled/enabled
 * @param id
 * @param isDisabled
 */
function event_disabled(id, isDisabled) {
    $(id).prop('disabled', isDisabled);
}

/**
 * Event: hide
 * @param id
 */
function event_hide(id) {
    $(id).hide();
}

/**
 * Event: show
 * @param id
 */
function event_show(id) {
    $(id).show();
}

/**
 * Event: set source image
 * @param id
 * @param src
 */
function setSrcImage(id, src) {
    $(id).attr('src', src);
}
