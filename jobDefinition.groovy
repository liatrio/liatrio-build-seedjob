folder = folder("pipelineJobs") {
  authorization {
    permission('hudson.model.View.Delete:administrators')
    permission('com.cloudbees.plugins.credentials.CredentialsProvider.Update:administrators')
    permission('hudson.model.View.Delete:administrators')
    permission('hudson.model.Item.Create:administrators')
    permission('hudson.model.Run.Delete:administrators')
    permission('hudson.model.Item.Workspace:administrators')
    permission('com.cloudbees.plugins.credentials.CredentialsProvider.Delete:administrators')
    permission('com.cloudbees.plugins.credentials.CredentialsProvider.ManageDomains:administrators')
    permission('hudson.model.View.Configure:administrators')
    permission('hudson.model.Run.Replay:administrators')
    permission('hudson.model.Item.Configure:administrators')
    permission('hudson.model.View.Read:administrators')
    permission('hudson.model.View.Create:administrators')
    permission('hudson.model.Item.Cancel:administrators')
    permission('hudson.model.Item.Delete:administrators')
    permission('hudson.model.Item.Read:administrators')
    permission('com.cloudbees.plugins.credentials.CredentialsProvider.View:administrators')
    permission('com.cloudbees.plugins.credentials.CredentialsProvider.Create:administrators')
    permission('hudson.model.Item.Build:administrators')
    permission('hudson.scm.SCM.Tag:administrators')
    permission('hudson.model.Item.Move:administrators')
    permission('hudson.model.Item.Discover:administrators')
    permission('hudson.model.Run.Update:administrators')
  }
}


def repos = readFileFromWorkspace("repos.txt").split()

repos.each {
  def jobName = it.split("/")[4].replaceAll(".git", "")
  pipelineJob("pipelineJobs/${jobName}"){
    definition {
      cpsScm {
        scm {
          git(it, null)
        }
        scriptPath('Jenkinsfile')
      }
    }
  }
}

//folder has auth via LDAP
//folder has shared library (between all jobs)
//add README.md
