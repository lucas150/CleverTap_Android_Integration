"use strict";

const functions = require("firebase-functions");
const admin = require("firebase-admin");
const https = require("https");
var request = require("requestretry");


admin.initializeApp();

exports.sendAndroidUninstallToCleverTap = functions.analytics.event("app_remove").onLog((event) => {

    //console.log("Event is: " + JSON.stringify(event));

    function myRetryStrategy(err, response, body, options) {
        // retry the request if we had an error or if the response was a "Bad Gateway"
        return !!err || response.statusCode === 503;
    }

    var clevertapId = event.user.userProperties.ct_objectId.value;
    // This is where the CleverTap ID of the user who uninstalled the app is passed as an identifier in the API call.
    const data = JSON.stringify({
       "d": [{
            "objectId": clevertapId,
            "type": "event",
            "evtName" : "App Uninstalled",
            "evtData": {
            }
        }]
    });

    request({
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-CleverTap-Account-Id": "TEST-4R8-7ZK-6K7Z",
            "X-CleverTap-Passcode": "AOE-BOB-OLEL"
        },
        body: data,
        url: "https://api.clevertap.com/firebase/upload",
//include dashboard region as a prefix in the URL <https://in1.api.clevertap.com/firebase/upload>

        // The below parameters are specific to request-retry
        maxAttempts: 5, // (default) try 5 times
        retryDelay: 2000, // (default) wait for 2s before trying again
        retryStrategy: myRetryStrategy // (default) retry on 5xx or network errors
    }, function (err, response, body) {
        // this callback will only be called when the request succeeded or after maxAttempts or on error
        if (response && response.statusCode === 200) {
            console.log("Response Body: " + JSON.stringify(body));
            console.log("The number of request attempts: " + response.attempts);
            return 0;
        }else{
            console.log("err: " + err + " ,response: " + JSON.stringify(response) + " , body: " + JSON.stringify(body));
            return 1;
        }
    });


});


// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });