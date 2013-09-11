package myPlugin;


/* This class represents a checkbox, and since it implements Describable it has its own 
 * config.jelly file for the GUI in jenkins */

import java.util.logging.Logger;

import hudson.Extension;
import hudson.model.Describable;
import hudson.model.Descriptor;
import hudson.model.Hudson;

import org.kohsuke.stapler.DataBoundConstructor;

public class Checkbox implements Describable<Checkbox> 
{
    private final String name;
    private final boolean selected;
    private final static Logger LOG = Logger.getLogger(Checkbox.class.getName());
    
    @DataBoundConstructor
    public Checkbox(final String name, final boolean selected) 
    {
        this.name = name;
        this.selected = selected;
    }

    public String getName() {
        return name;
    }

    public boolean isSelected() {
        return selected;
    }

    public Descriptor<Checkbox> getDescriptor() 
    {
        return Hudson.getInstance().getDescriptor(getClass());
    }
    
    @Extension
    public static class DescriptorImpl extends Descriptor<Checkbox> 
    {
        @Override
        public String getDisplayName() 
        {
            return "A Box!";
        }
    }
}
