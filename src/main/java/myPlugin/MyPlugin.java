// Even a warning example, is an example. :-X

package myPlugin;

import hudson.Extension;
import hudson.Launcher;
import hudson.model.BuildListener;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.Descriptor.FormException;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;

import java.util.ArrayList;
import java.util.logging.Logger;

import net.sf.json.JSONObject;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;

public class MyPlugin extends Builder {
	private final String name;
	public final boolean isSelected;
	public ArrayList<Checkbox> testlist;
	private final static Logger LOG = Logger
			.getLogger(MyPlugin.class.getName());

	// Fields in config.jelly must match the parameter names in the
	// "DataBoundConstructor"
	@DataBoundConstructor
	public MyPlugin(String name, boolean isSelected,
			ArrayList<Checkbox> testlist) {
		this.name = name;
		this.isSelected = isSelected;
		this.testlist = testlist;
		LOG.info("Constructor");
	}

	@Override
	public boolean perform(AbstractBuild build, Launcher launcher,
			BuildListener listener) {
		// This is what will be executed when the job is build.
		// This also shows how you can use listner and build.

		// Will be seen in the jenkins Console output
		listener.getLogger().println("The name of the test is: " + name);

		if (isSelected)
			listener.getLogger().println("Test report is created.");

		listener.getLogger().println(
				"This is job number: " + build.getDisplayName());
		return true;
	}

	// We'll use this from the config.jelly
	public String getName() {
		return name;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public ArrayList<Checkbox> getTestlist() {
		return testlist;
	}

	@Extension
	public static final DescriptorImpl DESCRIPTOR = new DescriptorImpl();
	
	// Overridden for better type safety.
	@Override
	public DescriptorImpl getDescriptor() {
		LOG.info("Tittut");
		return DESCRIPTOR;
	}

	@Extension
	// This indicates to Jenkins that this is an implementation of an extension
	// point.
	public static final class DescriptorImpl extends
			BuildStepDescriptor<Builder> {
		private final static Logger LOG = Logger.getLogger(DescriptorImpl.class
				.getName());

		public boolean isApplicable(Class<? extends AbstractProject> aClass) {
			// Indicates that this builder can be used with all kinds of project
			// types
			return true;
		}

		// This human readable name is used in the configuration screen.
		public String getDisplayName() {
			return "MyPlugin Test";
		}

		@Override
		public boolean configure(StaplerRequest req, JSONObject json)
				throws FormException {
			LOG.info("configure: " + json.toString());
			return super.configure(req, json);
		}

		@Override
		public void load() {
			LOG.info("load!");
			super.load();
		}

		// repopulate the saved form data
		@Override
		public Builder newInstance(StaplerRequest req, JSONObject formData)
				throws FormException {
			LOG.info("newInstance: " + formData.toString());
			return req.bindJSON(MyPlugin.class, formData);
			// return super.newInstance(req, formData);
		}
	}
}
