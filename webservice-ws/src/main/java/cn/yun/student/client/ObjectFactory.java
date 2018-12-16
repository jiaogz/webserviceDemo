
package cn.yun.student.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cn.yun.student.service package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetStudentInfo_QNAME = new QName("http://service.student.yun.cn/", "getStudentInfo");
    private final static QName _GetStudentInfoResponse_QNAME = new QName("http://service.student.yun.cn/", "getStudentInfoResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cn.yun.student.service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetStudentInfo }
     * 
     */
    public GetStudentInfo createGetStudentInfo() {
        return new GetStudentInfo();
    }

    /**
     * Create an instance of {@link GetStudentInfoResponse }
     * 
     */
    public GetStudentInfoResponse createGetStudentInfoResponse() {
        return new GetStudentInfoResponse();
    }

    /**
     * Create an instance of {@link Student }
     * 
     */
    public Student createStudent() {
        return new Student();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetStudentInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.student.yun.cn/", name = "getStudentInfo")
    public JAXBElement<GetStudentInfo> createGetStudentInfo(GetStudentInfo value) {
        return new JAXBElement<GetStudentInfo>(_GetStudentInfo_QNAME, GetStudentInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetStudentInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.student.yun.cn/", name = "getStudentInfoResponse")
    public JAXBElement<GetStudentInfoResponse> createGetStudentInfoResponse(GetStudentInfoResponse value) {
        return new JAXBElement<GetStudentInfoResponse>(_GetStudentInfoResponse_QNAME, GetStudentInfoResponse.class, null, value);
    }

}
