def jenkinsEnvironment = 'default'

try {
    if ( environment )
        jenkinsEnvironment = environment
    println "Using production jenkinsfiles"
}
catch (e) {
    println "Using default jenkinsfiles"
}


def pipelineJobFolder = organizationFolder("Liatrio")
pipelineJobFolder.with {
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

  organizations {
    github {
      repoOwner('Liatrio')
      scanCredentialsId('Github Creds')
    }
  }

  properties {
    folderLibraries {
        libraries {
          libraryConfiguration {
                name('ldop-shared-library')
                defaultVersion('master')
                implicit(true)
                retriever {
                    modernSCM {
                        scm {
                            git {
                                remote('https://github.com/liatrio/ldop-shared-library.git')
                            }
                        }
                    }
                }
            }
        }
    }
  }
}
