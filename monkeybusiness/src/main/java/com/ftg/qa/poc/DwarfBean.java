package com.ftg.qa.poc;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;
import java.net.URI;

public class DwarfBean implements Dwarf
{
    private int _age;
    private int[] _fortuneNumber;
    private double _height;
    private String _homeDir;
    private URI _homePage;
    private final PropertyChangeSupport _pcSupport;
    private final VetoableChangeSupport _vcSupport;
    private double _weight;

    public DwarfBean()
    {
        _pcSupport = new PropertyChangeSupport(this);
        _vcSupport = new VetoableChangeSupport(this);
    }

    public int getAge()
    {
        return _age  ;
    }


	public void setAge(int value)
    {
        int old = _age;

        _age = value;

        _pcSupport.firePropertyChange(PROP_AGE, old, value);
    }

    public int[] getFortuneNumber()
    {
        return _fortuneNumber;
    }

    public void setFortuneNumber(int[] value)
    {
        _fortuneNumber = value;
    }

    public double getHeight()
    {
        return _height;
    }

    public void setHeight(double value) throws PropertyVetoException
    {
        _vcSupport.fireVetoableChange(PROP_HEIGHT, _height, value);
        double old = _height;

        _height = value;

        _pcSupport.firePropertyChange(PROP_HEIGHT, old, value);
    }

    public String getHomeDir()
    {
        return _homeDir;
    }

    public void setHomeDir(String value)
    {
        String old = _homeDir;

        _homeDir = value;

        _pcSupport.firePropertyChange(PROP_HOME_DIR, old, value);
    }

    public URI getHomePage()
    {
        return _homePage;
    }

    public void setHomePage(URI value)
    {
        URI old = _homePage;

        _homePage = value;

        _pcSupport.firePropertyChange(PROP_HOME_PAGE, old, value);
    }

    public double getWeight()
    {
        return _weight;
    }

    public void setWeight(double value)
    {
        double old = _weight;

        _weight = value;

        _pcSupport.firePropertyChange(PROP_WEIGHT, old, value);
    }

    public void addPropertyChangeListener(String property, PropertyChangeListener listener)
    {
        _pcSupport.addPropertyChangeListener(property, listener);
    }

    public void addVetoableChangeListener(String property, VetoableChangeListener listener)
    {
        _vcSupport.addVetoableChangeListener(property, listener);
    }

    public boolean hasAge()
    {
        return _age != 0;
    }

    public boolean hasHeight()
    {
        return _height != 0.0;
    }

    public boolean hasHomePage()
    {
        return _homePage != null;
    }

    public boolean hasWeight()
    {
        return _weight != 0.0;
    }

    public void removePropertyChangeListener(String property, PropertyChangeListener listener)
    {
        _pcSupport.removePropertyChangeListener(property, listener);
    }

    public void removeVetoableChangeListener(String property, VetoableChangeListener listener)
    {
        _vcSupport.removeVetoableChangeListener(property, listener);
    }
}

