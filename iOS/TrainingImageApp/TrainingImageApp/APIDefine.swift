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

let serverUrl: String = {
	switch enviroment {
	case .dev:
		return "http://192.168.43.93:8080/api"
	case .test:
		return "Enviroment.TEST HERE"
	case .production:
		return "Enviroment.PRODUCTION HERE"
	}
}()


// MARK :- URL API
/* {
 "http://localhost:8080/api"
 Content-Type: application/json
 
 "ApiKey": "20e0a115-c1e8-4b6e-91ed-1f8e676b60b3",
 "userId": "1"
} */

let urlGetUserDetail: String = serverUrl.stringByAppendingString("/face/identifyImage")
let urlGetDepartment: String = serverUrl.stringByAppendingString("/department/findAll")
let urlGetAccountList: String = serverUrl.stringByAppendingString("/account/listAll")