package se.yrgo.erik.studentclient.dataretrieval.parsers;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import se.yrgo.erik.studentclient.dataretrieval.DataParser;
import se.yrgo.erik.studentclient.dataretrieval.DataParserFactory;
import se.yrgo.erik.studentclient.formatables.Course;
import se.yrgo.erik.studentclient.formatables.Formatable;
import se.yrgo.erik.studentclient.formatables.FormatableItem;
import se.yrgo.erik.studentclient.formatables.FormatableType;
import se.yrgo.erik.studentclient.formatables.Student;

public class XMLDataParser implements DataParser {

  private static final String TAG = "XMLDataParser";
  private static final String FAIL_MESSAGE = "Failed to parse XMLdata";

  static {
    DataParserFactory.register("xml", new XMLDataParser());
  }

  private XMLDataParser() {}

  @Override
  public List<Formatable> string2Students(String XMLdata) throws DataParserException {
    List<Formatable> returningStudents = new ArrayList<>();
    Document document = parseXML(XMLdata);
    if (document == null) {
      Log.v(TAG, "Parsing Students: " + FAIL_MESSAGE);
      throw new DataParserException(FAIL_MESSAGE);
    } else {
      List<Element> studentElements = findElements(document, "students");
      for (Element currentStudent : studentElements) {
        if (studentIsDetailed(currentStudent)) {
          returningStudents.add(extractDetailedStudent(currentStudent));
        } else {
          returningStudents.add(extractSimpleStudent(currentStudent));
        }
      }
    }
    Log.v(TAG, "Returning " + returningStudents.size() + " students");
    return returningStudents;
  }

  @Override
  public List<Formatable> string2Courses(String XMLdata) throws DataParserException {
    List<Formatable> returningCourses = new ArrayList<>();
    Document document = parseXML(XMLdata);
    if (document == null) {
      Log.v(TAG, "Parsing Courses: " + FAIL_MESSAGE);
      throw new DataParserException(FAIL_MESSAGE);
    } else {
      List<Element> courseElements = findElements(document, "courses");
      for (Element currentCourse : courseElements) {
        if (courseIsDetailed(currentCourse)) {
          returningCourses.add(extractDetailedCourse(currentCourse));
        } else {
          returningCourses.add(extractSimpleCourse(currentCourse));
        }
      }
    }
    Log.v(TAG, "Returning " + returningCourses.size() + " courses");
    return returningCourses;
  }

  private Formatable extractPhoneNumbers(Element currentStudent) {
    Map<String, String> phoneMap = new HashMap<>();
    Element phoneElement = (Element) ((Element) currentStudent
            .getElementsByTagName("phonenumbers").item(0))
            .getElementsByTagName("phonenumbers").item(0); //Yes it should be two steps - derp.
    if (phoneElement != null) {
      for (int k = 0; k < phoneElement.getChildNodes().getLength(); k++) {
        if (phoneElement.getChildNodes().item(k).getNodeType() == Node.ELEMENT_NODE) {
          phoneMap.put(phoneElement.getChildNodes().item(k).getNodeName(),
                  phoneElement.getChildNodes().item(k).getTextContent());
        }
      }
    }
    return new FormatableItem(FormatableType.PHONENUMBERS, 1, phoneMap);
  }

  private List<Course> extractCoursesFromStudent(Element courseElement) {
    List<Course> courses = new ArrayList<>();
    NodeList coursenodes = courseElement.getChildNodes();
    for (int l = 0; l < courseElement.getChildNodes().getLength(); l++) {
      Node coursenode = coursenodes.item(l);
      if (coursenode.getNodeType() == Node.ELEMENT_NODE) {
        Element currentCourse = (Element) coursenode;
        Map<String, String> properties = new HashMap<>();
        properties.put("name", currentCourse.getElementsByTagName("name").item(0).getTextContent());
        properties.put("status", currentCourse.getElementsByTagName("status").item(0)
                .getTextContent());
        properties.put("grade", currentCourse.getElementsByTagName("grade").item(0)
                .getTextContent());
        int courseId = Integer.parseInt(currentCourse.getAttribute("id"));
        courses.add(new Course(courseId, properties));
      }
    }
    return courses;
  }

  private Student extractSimpleStudent(Element currentStudent) {
    int studentId = Integer.parseInt(currentStudent.getAttribute("id"));
    String name = currentStudent.getElementsByTagName("name").item(0).getTextContent();
    String surname = currentStudent.getElementsByTagName("surname").item(0).getTextContent();
    return new Student(studentId, name, surname);
  }

  private Student extractDetailedStudent(Element currentStudent) {
    int studentId = Integer.parseInt(currentStudent.getAttribute("id"));
    String name = currentStudent.getElementsByTagName("name").item(0).getTextContent();
    String surname = currentStudent.getElementsByTagName("surname").item(0).getTextContent();
    int age = Integer.parseInt(currentStudent.getElementsByTagName("age").item(0).getTextContent());
    String postAddress = currentStudent.getElementsByTagName("postAddress").item(0)
            .getTextContent();
    String streetAddress = currentStudent.getElementsByTagName("streetAddress").item(0)
            .getTextContent();
    Formatable phonenumbers = extractPhoneNumbers(currentStudent);
    Element courseElement = (Element) currentStudent.getElementsByTagName("course").item(0);
    if (courseElement == null) {
      return new Student(studentId, name, surname, age, postAddress, streetAddress, phonenumbers);
    } else {
      List<Course> courses = extractCoursesFromStudent(courseElement);
      return new Student(studentId, name, surname, age, postAddress, streetAddress, phonenumbers,
              courses);
    }
  }

  private Course extractSimpleCourse(Element currentCourse) {
    int courseId = Integer.parseInt(currentCourse.getAttribute("id"));
    String name = currentCourse.getElementsByTagName("name").item(0).getTextContent();
    return new Course(courseId, name);
  }

  private Course extractDetailedCourse(Element currentCourse) {
    int courseId = Integer.parseInt(currentCourse.getAttribute("id"));
    String name = currentCourse.getElementsByTagName("name").item(0).getTextContent();
    String description = currentCourse.getElementsByTagName("description").item(0).getTextContent();
    String startDate = currentCourse.getElementsByTagName("startDate").item(0).getTextContent();
    String endDate = currentCourse.getElementsByTagName("endDate").item(0).getTextContent();
    int points = Integer.parseInt(currentCourse.getElementsByTagName("points").item(0)
            .getTextContent());
    Element studentElement = (Element) currentCourse.getElementsByTagName("student").item(0);
    List<Student> students = extractStudentsFromCourse(studentElement);
    return new Course(courseId, startDate, endDate, name, points, description, students);
  }

  private List<Student> extractStudentsFromCourse(Element studentElement) {
    List<Student> students = new ArrayList<>();
    if (studentElement != null) {
      NodeList studentNodes = studentElement.getChildNodes();
      for (int l = 0; l < studentElement.getChildNodes().getLength(); l++) {
        Node studentnode = studentNodes.item(l);
        if (studentnode.getNodeType() == Node.ELEMENT_NODE) {
          Element currentStudent = (Element) studentnode;
          Map<String, String> properties = new HashMap<>();
          properties.put("name", currentStudent.getElementsByTagName("name").item(0)
                  .getTextContent());
          properties.put("surname", currentStudent.getElementsByTagName("surname").item(0)
                  .getTextContent());
          properties.put("status", currentStudent.getElementsByTagName("status").item(0)
                  .getTextContent());
          int studentId = Integer.parseInt(currentStudent.getAttribute("id"));
          students.add(new Student(studentId, properties));
        }
      }
    }
    return students;
  }

  private Boolean studentIsDetailed(Element currentStudent) {
    return currentStudent.getElementsByTagName("age").item(0) != null;
  }

  private Boolean courseIsDetailed(Element currentCourse) {
    return currentCourse.getElementsByTagName("description").item(0) != null;
  }

  private List<Element> findElements(Document document, String name) {
    List<Element> foundElements = new ArrayList<>();
    NodeList nodeList = document.getDocumentElement().getChildNodes();
    for (int i = 0; i < nodeList.getLength(); i++) {
      Node node = nodeList.item(i);
      if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals(name)) {
        Element foundBaseElement = (Element) node;
        NodeList foundNodes = foundBaseElement.getChildNodes();
        for (int j = 0; j < foundBaseElement.getChildNodes().getLength(); j++) {
          Node currentNode = foundNodes.item(j);
          if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
            Element currentElement = (Element) currentNode;
            foundElements.add(currentElement);
          }
        }
      }
    }
    return foundElements;
  }

  private Document parseXML(String xmlData) {
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      InputStream inputStream;
      try {
        inputStream = new ByteArrayInputStream(xmlData.getBytes(StandardCharsets.UTF_8));
      } catch (NullPointerException npe) {
        return null;
      }
      return builder.parse(inputStream);
    } catch (SAXException | IOException | ParserConfigurationException e) {
      return null;
    }
  }

}