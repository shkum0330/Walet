#include <SPI.h>
#include <MFRC522.h>
#include <SoftwareSerial.h>

#define SS_PIN 10
#define RST_PIN 9
MFRC522 mfrc522(SS_PIN, RST_PIN); 
SoftwareSerial BTSerial(4, 5);
unsigned long previousReadTime = 0; 
const unsigned long interval = 1000; 

void setup() {
  Serial.begin(9600);
  SPI.begin();
  mfrc522.PCD_Init();
  BTSerial.begin(9600);
}

void loop() {
  unsigned long currentMillis = millis();

  if (currentMillis - previousReadTime >= interval) { 
    if (!mfrc522.PICC_IsNewCardPresent()) {
      previousReadTime = currentMillis;
      return;
    }
    if (!mfrc522.PICC_ReadCardSerial()) {
      previousReadTime = currentMillis;
      return;
    }

    Serial.print("UID tag :");
    String content = "";
    byte letter;
    for (byte i = 0; i < mfrc522.uid.size; i++) {
      Serial.print(mfrc522.uid.uidByte[i] < 0x10 ? " 0" : "");
      Serial.print(mfrc522.uid.uidByte[i], HEX);
      content.concat(String(mfrc522.uid.uidByte[i] < 0x10 ? "0" : ""));
      content.concat(String(mfrc522.uid.uidByte[i], HEX));
    }
    Serial.println();
    content.toUpperCase();

    if (content.substring(1) == "FA06F181") {
      Serial.println("Authorized access");

      BTSerial.print("Authorized access - RFID Tag: ");
      BTSerial.println(content);
      BTSerial.println();

      delay(3000);
    } else {
      Serial.println("Unauthorized access");

      BTSerial.println("Unauthorized access - RFID Tag: " + content);
    }

    previousReadTime = currentMillis;
  }

  while (BTSerial.available()) {
    byte data = BTSerial.read();
    Serial.write(data);
  }

  while (Serial.available()) {
    byte data = Serial.read();
    BTSerial.write(data);
  }
}
