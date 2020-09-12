package com.sptrainer.domain.util;

import com.sptrainer.domain.model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SQLUtil {
    /*USUARIO*/
    public static User getUser(ResultSet rs) throws SQLException {
        User u = new User();
        u.setId(rs.getLong("id"));
        u.setDocument(rs.getString("document"));
        u.setFirstname(rs.getString("firstname"));
        u.setLastname(rs.getString("lastname"));
        u.setBirthdate(rs.getString("birthdate"));
        u.setPhone(rs.getString("phone"));
        u.setPassword(rs.getString("password"));
        u.setUser(rs.getString("user"));
        u.setRolid(rs.getString("rolid"));
        u.setEmail(rs.getString("email"));
        u.setWeight(rs.getDouble("weight"));
        u.setHeight(rs.getDouble("height"));
        u.setDirection(rs.getString("direction"));
        u.setCity(rs.getString("city"));
        u.setDepartment(rs.getString("department"));
        u.setProfile(rs.getString("profile"));
        u.setSex(rs.getString("sex"));
        u.setPhoto(rs.getString("photo"));
        u.setCoordinate(rs.getString("coordinate"));
        u.setDate_save(rs.getString("date_save"));
        u.setDate_update(rs.getString("date_update"));
        return u;
    }

    public static PreparedStatement setDataUser(PreparedStatement ps, User user) throws SQLException {
        ps.setString(1, user.getDocument());
        ps.setString(2, user.getFirstname());
        ps.setString(3, user.getLastname());
        ps.setString(4, user.getBirthdate());
        ps.setString(5, user.getPhone());
        ps.setString(6, user.getPassword());
        ps.setString(7, user.getUser());
        ps.setString(8, user.getRolid());
        ps.setString(9, user.getEmail());
        if (user.getWeight() != null) ps.setDouble(10, user.getWeight());
        else ps.setNull(10, java.sql.Types.NULL);
        if (user.getHeight() != null) ps.setDouble(11, user.getHeight());
        else ps.setNull(11, java.sql.Types.NULL);
        ps.setString(12, user.getDirection());
        ps.setString(13, user.getCity());
        ps.setString(14, user.getDepartment());
        ps.setString(15, user.getProfile());
        ps.setString(16, user.getSex());
        ps.setString(17, user.getPhoto());
        ps.setString(18, user.getCoordinate());
        return ps;
    }

    /*GRUPOS DE ENTRAMIENTO*/
    public static GroupTrainer getGroupTrainer(ResultSet rs) throws SQLException {
        GroupTrainer g = new GroupTrainer();
        g.setId(rs.getLong("id"));
        g.setIduser(rs.getLong("iduser"));
        g.setName(rs.getString("name"));
        g.setDescription(rs.getString("description"));
        g.setQuantity(rs.getInt("quantity"));
        g.setActive(rs.getString("active"));
        g.setState(rs.getString("state"));
        g.setEnddate(rs.getString("enddate"));
        g.setStartdate(rs.getString("startdate"));
        g.setType_schedule(rs.getString("type_schedule"));
        g.setAddress(rs.getString("address"));
        g.setSitie(rs.getString("sitie"));
        g.setCoordinate(rs.getString("coordinate"));
        g.setColour(rs.getString("colour"));
        g.setCategory(rs.getString("category"));
        g.setDate_save(rs.getString("date_save"));
        g.setDate_update(rs.getString("date_update"));
        g.setDate_publish(rs.getString("date_publish"));
        return g;
    }

    public static PreparedStatement setDataGroupTrainer(PreparedStatement ps, GroupTrainer g) throws SQLException {
        ps.setLong(1, g.getIduser());
        ps.setString(2, g.getName());
        ps.setString(3, g.getDescription());
        ps.setInt(4, g.getQuantity());
        ps.setString(5, g.getActive());
        ps.setString(6, g.getState());
        ps.setString(7, g.getEnddate());
        ps.setString(8, g.getStartdate());
        ps.setString(9, g.getType_schedule());
        ps.setString(10, g.getAddress());
        ps.setString(11, g.getSitie());
        ps.setString(12, g.getCoordinate());
        ps.setString(13, g.getColour());
        ps.setString(14, g.getCategory());
        return ps;
    }

    /*SESIONES DE ENTRAMIENTO*/
    public static SesionTrainer getSesionTrainer(ResultSet rs) throws SQLException {
        SesionTrainer s = new SesionTrainer();
        s.setId(rs.getLong("id"));
        s.setIdgroup(rs.getLong("idgroup"));
        s.setIduser(rs.getLong("iduser"));
        s.setStartday(rs.getString("startday"));
        s.setEndday(rs.getString("endday"));
        s.setStarthour(rs.getString("starthour"));
        s.setEndhour(rs.getString("endhour"));
        s.setActive(rs.getString("active"));
        s.setAddress(rs.getString("address"));
        s.setDescription(rs.getString("description"));
        s.setCoordinate(rs.getString("coordinate"));
        s.setName(rs.getString("name"));
        s.setSitiedefault(rs.getString("sitiedefault"));
        s.setDate_save(rs.getString("date_save"));
        s.setDate_update(rs.getString("date_update"));
        return s;
    }

    public static PreparedStatement setDataSesionTrainer(PreparedStatement ps, SesionTrainer s) throws SQLException {
        ps.setLong(1, s.getIdgroup());
        ps.setLong(2, s.getIduser());
        ps.setString(3, s.getStartday());
        ps.setString(4, s.getEndday());
        ps.setString(5, s.getStarthour());
        ps.setString(6, s.getEndhour());
        ps.setString(7, s.getActive());
        ps.setString(8, s.getAddress());
        ps.setString(9, s.getDescription());
        ps.setString(10, s.getCoordinate());
        ps.setString(11, s.getName());
        ps.setString(12, s.getSitiedefault());
        return ps;
    }

    // SOLICTUD DE REGISTRO
    public static PreparedStatement setDataRegistrationRequest(PreparedStatement ps, RegistrationRequest r) throws SQLException {
        ps.setLong(1, r.getIduser());
        ps.setLong(2, r.getIdgroup());
        ps.setString(3, r.getState());
        ps.setString(4, r.getComment());
        return ps;
    }

    public static RegistrationRequest getRegistrationRequest(ResultSet rs) throws SQLException {
        RegistrationRequest r = new RegistrationRequest();
        r.setId(rs.getLong("id"));
        r.setIduser(rs.getLong("iduser"));
        r.setIdgroup(rs.getLong("idgroup"));
        r.setState(rs.getString("state"));
        r.setAnswer(rs.getString("answer"));
        r.setComment(rs.getString("comment"));
        r.setDateregistration(rs.getString("dateregistration"));
        r.setDateanswer(rs.getString("dateanswer"));
        return r;
    }

    // MIEMBROS
    public static PreparedStatement setDataMember(PreparedStatement ps, Member m) throws SQLException {
        ps.setLong(1, m.getIduser());
        ps.setLong(2, m.getIdgroup());
        ps.setString(3, m.getState());
        return ps;
    }

    /*SESIONES DE ENTRAMIENTO*/
    public static Member getMember(ResultSet rs) throws SQLException {
        Member m = new Member();
        m.setIduser(rs.getLong("iduser"));
        m.setIdgroup(rs.getLong("idgroup"));
        m.setState(rs.getString("state"));
        m.setDate_save(rs.getString("date_save"));
        return m;
    }

    //CALIFICATION
    public static PreparedStatement setDataCalificate(PreparedStatement ps, Calification c) throws SQLException {
        ps.setLong(1, c.getIdgroup());
        ps.setLong(2, c.getIduser());
        if(c.getIdsesion()!=null) ps.setLong(3, c.getIdsesion()); else ps.setNull(3, java.sql.Types.NULL);
        if(c.getScore()!=null) ps.setLong(4, c.getScore()); else ps.setNull(4, java.sql.Types.NULL);
        if(c.getIdusercalificate()!=null) ps.setLong(5, c.getIdusercalificate()); else ps.setNull(5, java.sql.Types.NULL);
        return ps;
    }

    public static Calification getCalificate(ResultSet rs) throws SQLException {
        Calification c = new Calification();
        c.setId(rs.getLong("id"));
        c.setIduser(rs.getLong("iduser"));
        c.setIdgroup(rs.getLong("idgroup"));
        c.setIdsesion(rs.getLong("idsesion"));
        c.setScore(rs.getLong("score"));
        c.setIdusercalificate(rs.getLong("idusercalificate"));
        c.setDatecalificate(rs.getString("datecalificate"));
        return c;
    }

    //NOTIFICATION
    public static PreparedStatement setDataNotification(PreparedStatement ps, Notification n) throws SQLException {
        ps=validateNull(ps,n.getIdgrup(),1,1);
        ps=validateNull(ps,n.getIduser(),2,1);
        ps=validateNull(ps,n.getColour(),3,2);
        ps.setString(4,n.getDescription());
        ps.setString(5,n.getType_notification());
        ps=validateNull(ps,n.getId_registration_request(),6,1);
        ps=validateNull(ps,n.getIdsesion(),7,1);
        ps.setString(8,n.getState());
        ps=validateNull(ps,n.getIdusergenerate(),9,1);
        return ps;
    }

    public static Notification getNotification(ResultSet rs) throws SQLException {
        Notification n = new Notification();
        n.setId(rs.getLong("id"));
        n.setIduser(rs.getLong("iduser"));
        n.setIdgrup(rs.getLong("idgroup"));
        n.setColour(rs.getString("colour"));
        n.setDescription(rs.getString("description"));
        n.setType_notification(rs.getString("type_notification"));
        n.setId_registration_request(rs.getLong("id_registration_request"));
        n.setIdsesion(rs.getLong("idsesion"));
        n.setState(rs.getString("state_read"));
        n.setDatenotificacion(rs.getString("datenotificacion"));
        n.setIdusergenerate(rs.getLong("idusergenerate"));
        return n;
    }


    public static PreparedStatement validateNull(PreparedStatement ps,Object obj,int n,int tp) throws SQLException {
        if(obj==null) ps.setNull(n,java.sql.Types.NULL);
        else{
           switch (tp){
               case 1:
                   ps.setLong(n,(Long)obj);
                   break;
               case 2:
                   ps.setString(n,(String)obj);
                   break;
           }
        }
        return ps;
    }

    public static String SQLINLong(List<Long> list) {
        String in = "";
        if (!list.isEmpty()) {
            in += "(";
            for (int i = 0; i < list.size(); i++) {
                in += (i == 0) ? "" + list.get(i) : "," + list.get(i);
            }
            in += ")";
        }
        return in;
    }

    public static String SQLINString(List<String> list) {
        String in = "";
        if (!list.isEmpty()) {
            in += "(";
            for (int i = 0; i < list.size(); i++) {
                in += (i == 0) ? "'" + list.get(i) + "'" : ",'" + list.get(i) + "'";
            }
            in += ")";
        }
        return in;
    }

    public static String validateEmpity(String sql,String where){
        if(sql.isEmpty()) return "";
        if(where.isEmpty()) return sql;
        return " AND "+sql;
    }
}
