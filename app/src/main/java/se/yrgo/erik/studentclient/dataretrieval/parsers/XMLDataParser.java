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

  static {
    DataParserFactory.register("xml", new XMLDataParser());
  }

  private XMLDataParser() {}

  @Override
  public List<Formatable> string2Students(String XMLdata) throws DataParserException {
    List<Formatable> returningStudents = new ArrayList<>();
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      InputStream is;
      try {
        is = new ByteArrayInputStream(XMLdata.getBytes(StandardCharsets.UTF_8));
      } catch (NullPointerException npe) {
        return returningStudents;
      }
      Document document = builder.parse(is);
      NodeList nodeList = document.getDocumentElement().getChildNodes();
      for (int i = 0; i < nodeList.getLength(); i++) {
        Node node = nodeList.item(i);
        if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("students")) {
          Element studentsElement = (Element) node;
          NodeList studentnodes = studentsElement.getChildNodes();
          for (int j = 0; j < studentsElement.getChildNodes().getLength(); j++) {
            Node studentnode = studentnodes.item(j);
            if (studentnode.getNodeType() == Node.ELEMENT_NODE) {
              Element currentStudent = (Element) studentnode;
              if (currentStudent.getElementsByTagName("age").item(0) != null) {
                int studentId = Integer.parseInt(currentStudent.getAttribute("id"));
                String name = currentStudent.getElementsByTagName("name").item(0)
                        .getTextContent();
                String surname = currentStudent.getElementsByTagName("surname")
                        .item(0).getTextContent();
                int age = Integer.parseInt(currentStudent.getElementsByTagName("age").item(0)
                        .getTextContent());
                String postAddress = currentStudent.getElementsByTagName("postAddress").item(0)
                        .getTextContent();
                String streetAddress = currentStudent.getElementsByTagName("streetAddress").item(0)
                        .getTextContent();


                Map<String, String> phoneMap = new HashMap<>();
                Element phoneElement = (Element) ((Element) currentStudent
                        .getElementsByTagName("phonenumbers").item(0))
                        .getElementsByTagName("phonenumbers").item(0);
                if (phoneElement != null) {
                  for (int k = 0; k < phoneElement.getChildNodes().getLength(); k++) {
                    if (phoneElement.getChildNodes().item(k).getNodeType() == Node.ELEMENT_NODE) {
                      phoneMap.put(phoneElement.getChildNodes().item(k).getNodeName(),
                              phoneElement.getChildNodes().item(k).getTextContent());
                    }
                  }
                }
                Formatable phonenumbers = new FormatableItem(FormatableType.PHONENUMBERS, 1, phoneMap);


                List<Course> courses = new ArrayList<>();
                Element courseElement = (Element) currentStudent.getElementsByTagName("course").item(0);
                if (courseElement != null) {
                  NodeList coursenodes = courseElement.getChildNodes();
                  for (int l = 0; l < courseElement.getChildNodes().getLength(); l++) {
                    Node coursenode = coursenodes.item(l);
                    if (coursenode.getNodeType() == Node.ELEMENT_NODE) {
                      Element currentCourse = (Element) coursenode;
                      Map<String, String> properties = new HashMap<>();
                      properties.put("name", currentCourse.getElementsByTagName("name").item(0)
                              .getTextContent());
                      properties.put("status", currentCourse.getElementsByTagName("status").item(0)
                              .getTextContent());
                      properties.put("grade", currentCourse.getElementsByTagName("grade").item(0)
                              .getTextContent());
                      int courseId = Integer.parseInt(currentCourse.getAttribute("id"));
                      courses.add(new Course(courseId, properties));
                    }

                  }
                  returningStudents.add(new Student(studentId, name, surname, age, postAddress,
                          streetAddress, phonenumbers, courses));
                }
              } else {
                int studentId = Integer.parseInt(currentStudent.getAttribute("id"));
                String name = currentStudent.getElementsByTagName("name").item(0)
                        .getTextContent();
                String surname = currentStudent.getElementsByTagName("surname")
                        .item(0).getTextContent();
                returningStudents.add(new Student(studentId, name, surname));
              }
            }
          }
        }
      }
    } catch (SAXException | IOException | ParserConfigurationException e) {
      Log.v("XMLPARSER", "Error parsing XML: " + e.getMessage());
    }
    Log.v("XMLPARSER", "Returning " + returningStudents.size() + " students");
    return returningStudents;
  }

  @Override
  public List<Formatable> string2Courses(String XMLdata) throws DataParserException {
    List<Formatable> returningCourses = new ArrayList<>();
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      InputStream is;
      try {
        is = new ByteArrayInputStream(XMLdata.getBytes(StandardCharsets.UTF_8));
      } catch (NullPointerException npe) {
        return returningCourses;
      }
      Document document = builder.parse(is);
      NodeList nodeList = document.getDocumentElement().getChildNodes();
      for (int i = 0; i < nodeList.getLength(); i++) {
        Node node = nodeList.item(i);
        if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("courses")) {
          Element coursesElement = (Element) node;
          NodeList coursenodes = coursesElement.getChildNodes();
          for (int j = 0; j < coursesElement.getChildNodes().getLength(); j++) {
            Node coursenode = coursenodes.item(j);
            if (coursenode.getNodeType() == Node.ELEMENT_NODE) {
              Element currentCourse = (Element) coursenode;
              if (currentCourse.getElementsByTagName("description").item(0) != null) {
                int courseId = Integer.parseInt(currentCourse.getAttribute("id"));
                String name = currentCourse.getElementsByTagName("name").item(0)
                        .getTextContent();
                String description = currentCourse.getElementsByTagName("description")
                        .item(0).getTextContent();
                String startDate = currentCourse.getElementsByTagName("startDate").item(0)
                        .getTextContent();
                String endDate = currentCourse.getElementsByTagName("endDate").item(0)
                        .getTextContent();
                int points = Integer.parseInt(currentCourse.getElementsByTagName("points").item(0)
                        .getTextContent());


                List<Student> students = new ArrayList<>();
                Element studentElement = (Element) currentCourse.getElementsByTagName("student").item(0);
                if (studentElement != null) {
                  NodeList studentnodes = studentElement.getChildNodes();
                  for (int l = 0; l < studentElement.getChildNodes().getLength(); l++) {
                    Node studentnode = studentnodes.item(l);
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
                  returningCourses.add(new Course(courseId, startDate, endDate, name, points,
                          description, students));
                }
              } else {
                int courseId = Integer.parseInt(currentCourse.getAttribute("id"));
                String name = currentCourse.getElementsByTagName("name").item(0)
                        .getTextContent();
                returningCourses.add(new Course(courseId, name));
              }
            }
          }
        }
      }
    } catch (SAXException | IOException | ParserConfigurationException e) {
      Log.v("XMLPARSER", "Error parsing XML: " + e.getMessage());
    }
    Log.v("XMLPARSER", "Returning " + returningCourses.size() + " courses");
    return returningCourses;
  }
}
