//
//  APIDefine.swift
//  Superb
//
//  Created by Le Thanh Tan on 6/22/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import Foundation

// Server
enum Enviroment {
	case dev
	case test
	case production
}

let enviroment = Enviroment.dev
//let enviroment = Enviroment.test
//let enviroment = Enviroment.production

let ip: String = "192.168.43.93"
let serverUrl: String = "http://" + ip + ":8080/api"


// MARK :- URL API
/* {
 "http://localhost:8080/api"
 Content-Type: application/json
 
 "ApiKey": "20e0a115-c1e8-4b6e-91ed-1f8e676b60b3",
 "userId": "1"
} */

let urlCheckIn: String = serverUrl.stringByAppendingString("/account/check_in")
let urlLogin: String = serverUrl.stringByAppendingString("/account/login")
let urlUpdateToken: String = serverUrl.stringByAppendingString("/account/update_token_id_mobile")
let urlGetReminder: String = serverUrl.stringByAppendingString("/account/get_reminder")
let urlGetAttance: String = serverUrl.stringByAppendingString("/time/get_attendance")