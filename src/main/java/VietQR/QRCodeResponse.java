/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VietQR;

/**
 *
 * @author ASUS
 */
public class QRCodeResponse {
    public String code;
    public String desc;
    public QRCodeResponseData data;

    public QRCodeResponse() {
    }

    public QRCodeResponse(String code, String desc, QRCodeResponseData data) {
        this.code = code;
        this.desc = desc;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public QRCodeResponseData getData() {
        return data;
    }

    public void setData(QRCodeResponseData data) {
        this.data = data;
    }
    
}
