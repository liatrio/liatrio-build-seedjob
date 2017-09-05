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
  properties {
    folderLibraries {
        libraries {
            libraryConfiguration {
                name('example_sharing')
                retriever {
                    modernSCM {
                        scm {
                            git {
                                id('73a88996-5cc1-4624-8fb5-8a148776e26e')
                                remote('https://github.com/liatrio/pipeline-library.git')
                        }
                    }
                }
            }
        }
    }
  }
}


def repos = readFileFromWorkspace("repos.txt")

repos.split("\n").each { repo->
  def jobName = repo.split("#")[0].split("/")[4].replaceAll(".git", "")
  def branch = repo.split("#")[1]
  def url = repo.split("#")[0]
  pipelineJob("pipelineJobs/${jobName}"){
    definition {
      cpsScm {
        scm {
          git(url, branch, null)
        }
        scriptPath('Jenkinsfile')
      }
    }
  }
}
