//
//  BeaconViewController.swift
//  StaffApp
//
//  Created by Le Thanh Tan on 10/10/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import UIKit
import CoreLocation
import RealmSwift

let top_margin = 150
let max_distance = 20

class BeaconViewController: BaseViewController, UIScrollViewDelegate {
  
  @IBOutlet weak var mapView: UIView!
  @IBOutlet weak var informationLabel: UILabel!
  @IBOutlet weak var scrollView: UIScrollView!
  @IBOutlet weak var textMapImageView: UIImageView!
  
  var beacon: CLBeacon? = nil
  var beaconManager: ESTBeaconManager? = nil
  var positionDot: UIImageView!
  var utilityManager: ESTUtilityManager!
  var region: CLBeaconRegion!
  let scale = 120.0
  var beacons: [CLBeacon] = [] {
    didSet {
      // update layout
      calculateDistance(beacons)
    }
  }
  let realm = try! Realm()
  
  let imageView = UIImageView(image: UIImage(named: "RoomFPTMap"))
  
  let ESTIMOTE_PROXIMITY_UUID = NSUUID(UUIDString: "B9407F30-F5F8-466E-AFF9-25556B57FE6D")
  
  override func viewDidLoad() {
    super.viewDidLoad()
    beaconManager = ESTBeaconManager()
    self.region = CLBeaconRegion(proximityUUID: ESTIMOTE_PROXIMITY_UUID!, identifier: "EstimoteSampleRegion")
    beaconManager!.delegate = self
    beaconManager!.requestAlwaysAuthorization()
    
    imageView.translatesAutoresizingMaskIntoConstraints = true
    scrollView.addSubview(imageView)
    scrollView.contentSize = imageView.frame.size    
    self.scrollView.maximumZoomScale = 5.0
    self.scrollView.minimumZoomScale = 0.5
    self.scrollView.delegate = self
    
    
//    let line1 = UIView(frame: CGRect(x: A.x, y: A.y, width: 10.0, height: 10.0))
//    line1.backgroundColor = UIColor.redColor()
//    line1.tag = 0
//    self.imageView.addSubview(line1)
//
//    let line2 = UIView(frame: CGRect(x: B.x, y: B.y, width: 10.0, height: 10.0))
//    line2.backgroundColor = UIColor.greenColor()
//    line2.tag = 1
//    self.imageView.addSubview(line2)
//    
//    let line3 = UIView(frame: CGRect(x: C.x, y: C.y, width: 10.0, height: 10.0))
//    line3.backgroundColor = UIColor.blueColor()
//    line3.tag = 0
//    self.imageView.addSubview(line3)

  }
  

  override func viewDidDisappear(animated: Bool) {
    
  }

}

extension BeaconViewController: ESTBeaconManagerDelegate {
  func beaconManager(manager: AnyObject, didChangeAuthorizationStatus status: CLAuthorizationStatus) {
    beaconManager?.startRangingBeaconsInRegion(self.region)
  }
  func beaconManager(manager: AnyObject, didRangeBeacons beacons: [CLBeacon], inRegion region: CLBeaconRegion) {
    if beacons.count > 0 {
      print("Number of beacon: \(beacons.count)")
      self.beacons = beacons
    }
  }
}

// MARK :- Private method
extension BeaconViewController {
  
  
  private func calculateDistance(beacons: [CLBeacon]) {
    if beacons.count >= 3 {
      let d1 = beacons[0].accuracy
      print("D1: \(d1)\n")
      let d2 = beacons[1].accuracy
      print("D2: \(d2)\n")
      let d3 = beacons[2].accuracy
      print("D3: \(d3)\n")
      
      let beacon1 = realm.objects(Beacon.self).filter("major = \(beacons[0].major)").first
      let beacon2 = realm.objects(Beacon.self).filter("major = \(beacons[1].major)").first
      let beacon3 = realm.objects(Beacon.self).filter("major = \(beacons[2].major)").first
      
      let A = (x: beacon1!.latitude, y: beacon1!.longitude)
      let B = (x: beacon2!.latitude, y: beacon2!.longitude)
      let C = (x: beacon3!.latitude, y: beacon3!.longitude)
      
      if (d1 != -1.0 && d2 != -1.0 && d3 != -1.0) {
        let a1 = 2 * (B.x - A.x)
        let b1 = 2 * (B.y - A.y)
        let c1 = pow(d1, 2) - pow(d2, 2) + pow(B.x, 2) - pow(A.x, 2) + pow(B.y, 2) - pow(A.y, 2)
        
        let a2 = 2 * (C.x - A.x)
        let b2 = 2 * (C.y - A.y)
        let c2 = pow(d1, 2) - pow(d3, 2) + pow(C.x, 2) - pow(A.x, 2) + pow(C.y, 2) - pow(A.y, 2)
        
        let D = a1 * b2 - a2 * b1
        let Dx = c1 * b2 - c2 * b1
        let Dy = a1 * c2 - a2 * c1
        
        if D != 0 {
          let x = Dx / D
          let y = Dy / D
          print("x: \(x)")
          print("y: \(y)")
          // check beacon inside area
          let dAB: Double = pow(A.x - B.x, 2) + pow(A.y - B.y, 2)
          let dAC: Double = pow(A.x - C.x, 2) + pow(A.y - C.y, 2)
          let dBC: Double = pow(B.x - C.x, 2) + pow(B.y - C.y, 2)
          let maxD = max(dAB, dAC, dBC)
          
          let approximate : Double = 2.0
          
//          print("Dang o dau do")
//          var areaName : [String : Int] = [:]
//          areaName[beacon1!.areaName] = areaName[beacon1!.areaName]! + 1
//          areaName[beacon2!.areaName] = areaName[beacon2!.areaName]! + 1
//          areaName[beacon3!.areaName] = areaName[beacon3!.areaName]! + 1
//          var max: (String, Int) = ("", 0)
//          for (key, value) in areaName {
//            
//          }
          switch maxD {
          case dAB:
            if A.x == C.x {
              // hinh 1
              if min(C.y, A.y) - approximate < y && y < max(C.y, A.y) + approximate &&
                min(B.x, C.x) + approximate < x && x < max(B.x, C.x) {
                print("I AM HERE dAB")
              } else {
                print("Khong xac dinh")
              }
            } else {
              // hinh 2
              if min(C.y, B.y) - approximate < y && y < max(C.y, B.y) + approximate &&
                min(A.x, C.x) - approximate < x && x < max(A.x, C.x) + approximate {
                print("I AM HERE dAB")
              } else {
                print("Khong xac dinh")
              }
            }
            break
          case dAC:
            if A.x == B.x {
              // hinh 1
              if min(B.y, A.y) - approximate < y && y < max(B.y, A.y) + approximate &&
                min(C.x, B.x) - approximate < x && x < max(C.x, B.x) + approximate {
                print("I AM HERE dAC")
              } else {
                print("Khong xac dinh")
              }
              
            } else {
              // hinh 2
              if min(B.y, C.y) - approximate < y && y < max(B.y, C.y) + approximate &&
                min(B.x, A.x) - approximate < x && x < max(B.x, A.x) + approximate {
                print("I AM HERE dAC")
              } else {
                print("Khong xac dinh")
              }
            }
            break
          case dBC:
            if A.x == B.x {
              // hinh 1
              if min(A.y, B.y) - approximate < y && y < max(A.y, B.y) + approximate &&
                min(A.x, C.x) - approximate < x && x < max(A.x, C.x) + approximate {
                print("I AM HERE dBC")
              } else {
                print("Khong xac dinh")
              }
            } else {
              // hinh 2
              if min(A.y, C.y) - approximate < y && y < max(A.y, C.y) + approximate &&
                min(A.x, B.x) - approximate < x && x < max(A.x, B.x) + approximate {
                print("I AM HERE dBC")
              } else {
                print("Khong xac dinh")
              }
            }
            break
            
          default:
            break
          }
          
        }
      }
    }
    
  }
}