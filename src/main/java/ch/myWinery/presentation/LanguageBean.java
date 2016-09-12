package ch.myWinery.presentation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "languageBean")
@SessionScoped
public class LanguageBean {

    private List<String> supportedLanguages = new ArrayList<String>();
    private Locale locale = null;

    public void setSupportedLanguages(List<String> supportedLanguages) {
        this.supportedLanguages = supportedLanguages;
    }

    public List<String> getSupportedLanguages() {
        if (supportedLanguages.isEmpty()) {
            Iterator<Locale> locs = FacesContext.getCurrentInstance().getApplication().getSupportedLocales();
            while (locs.hasNext()) {
                Locale loc = locs.next();
                supportedLanguages.add(loc.toString());
            }
        }

        return supportedLanguages;
    }

    public boolean isActiveLanguage(String lang) {
        return locale.toString().equals(lang);
    }

    public String setLocale(String locale) {
        for (String lang : supportedLanguages) {
            if (lang.equals(locale)) {
                this.locale = new Locale(locale);
                FacesContext.getCurrentInstance().getViewRoot().setLocale(this.locale);
            }
        }

        return "";
    }

    public Locale getLocale() {
        if (locale == null) {
            locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
            if (locale == null) {
                locale = Locale.GERMAN;
            }
        }
        return locale;
    }
}
