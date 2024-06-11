/*
 * Clase pensada para trabajo con fechas
 */
package miselaneos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author cesar
 */
public class Fecha {

    static String FORMATOMYSQL = "yyyy-MM-dd";
    static String FORMATO_YYYYMMDDhhmm = "yyyy-MM-dd hh:mm";
    static String FORMATO_YYYYMMDDhhmm_am_pm = "yyyy-MM-dd hh:mm aa";
    static String FORMATO_YYYYMMDDhhmmss_am_pm = "yyyy-MM-dd hh:mm:ss aa";
    static String FORMATO_YYYY_MM_DD_hh_mm_ss = "yyyy_MM_dd_hh_mm_ss";
    static String FORMATO_hhmmss_am_pm = "hh:mm:ss aa";
    static String FORMATO_DDMMYYYY = "dd/MM/yyyy";
    static String FORMATO_hhmm_am_pm = "hh:mm aa";
    static String FORMATO_hh = "hh";
    private SimpleDateFormat SDateFormat;

    public Calendar getFechaACalendar(Date fecha) {
        SDateFormat = new SimpleDateFormat(FORMATOMYSQL);
        Calendar calendar = Calendar.getInstance();
        
        calendar.setTime(fecha);
        
        return calendar;
    }
    
    public Calendar getCalendar(java.sql.Timestamp timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp == null ? System.currentTimeMillis() : timestamp.getTime());
        return calendar;
    }
    
    public String getFechaMySQL(Calendar fecha) {
        SDateFormat = new SimpleDateFormat(FORMATOMYSQL);
        String fechaMySQL = SDateFormat.format(fecha.getTime());

        return fechaMySQL;
    }
    
    public String getFechaDDMMAAAA(Calendar fecha) {
        SDateFormat = new SimpleDateFormat(FORMATO_DDMMYYYY);
        String fechaDDMMYYY = SDateFormat.format(fecha.getTime());

        return fechaDDMMYYY;
    }

    public String gethhmmAMPM(Calendar fecha) {
        SDateFormat = new SimpleDateFormat(FORMATO_hhmm_am_pm);
        String Hmm = SDateFormat.format(fecha.getTime());
        return Hmm;
    }

    public String gethhmmssAMPM(Calendar fecha) {
        SDateFormat = new SimpleDateFormat(FORMATO_hhmmss_am_pm);
        String Hmm = SDateFormat.format(fecha.getTime());
        return Hmm;
    }

    public Calendar getFechaCalendarMYSQL(String fecha) {
        SDateFormat = new SimpleDateFormat(FORMATOMYSQL);
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTimeInMillis(SDateFormat.parse(fecha).getTime());
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        } finally {
            return calendar;
        }
    }

    public Calendar getFechaCalendarYYYYMMDDHHMM(String fecha) {
        SDateFormat = new SimpleDateFormat(FORMATO_YYYYMMDDhhmm);
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTimeInMillis(SDateFormat.parse(fecha).getTime());
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        } finally {
            return calendar;
        }
    }

    public String getYYYYMMDDhhmmssAMPM(Calendar fecha) {
        SDateFormat = new SimpleDateFormat(FORMATO_YYYYMMDDhhmmss_am_pm);
        String Hmm = SDateFormat.format(fecha.getTime());
        return Hmm;
    }

    public String getYYYY_MM_DD_hh_mm_ss(Calendar fecha) {
        SDateFormat = new SimpleDateFormat(FORMATO_YYYY_MM_DD_hh_mm_ss);
        String Hmm = SDateFormat.format(fecha.getTime());
        return Hmm;
    }

    public Calendar getYYYYMMDDhhmmssAMPM(String fecha) {
        SDateFormat = new SimpleDateFormat(FORMATO_YYYYMMDDhhmmss_am_pm);
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTimeInMillis(SDateFormat.parse(fecha).getTime());
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        } finally {
            return calendar;
        }
    }

    public String getYYYYMMDDhhmmAMPM(Calendar fecha) {
        SDateFormat = new SimpleDateFormat(FORMATO_YYYYMMDDhhmm_am_pm);
        String Hmm = SDateFormat.format(fecha.getTime());
        return Hmm;
    }

    public int getHora(String fecha) {
        SDateFormat = new SimpleDateFormat(FORMATO_hh);
        Calendar calendar = Calendar.getInstance();
        int horas = 0;
        try {
            calendar.setTimeInMillis(SDateFormat.parse(fecha).getTime());
            horas = calendar.get(Calendar.HOUR);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        } finally {
            return horas;
        }
    }

    public int getMinutos(String fecha) {
        SDateFormat = new SimpleDateFormat(FORMATO_hhmm_am_pm);
        Calendar calendar = Calendar.getInstance();
        int minutos = 0;
        try {
            calendar.setTimeInMillis(SDateFormat.parse(fecha).getTime());
            minutos = calendar.get(Calendar.MINUTE);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        } finally {
            return minutos;
        }
    }

    public String getDiaSemana(Calendar c) {
        SDateFormat = new SimpleDateFormat("EEE");
        String dia = "";
        try {
            dia = SDateFormat.format(c.getTime());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            return dia;
        }
    }

}
