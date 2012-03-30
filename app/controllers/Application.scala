package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models._
import play.api.templates._

object Application extends Controller {

  def index = Action {
    //Ok(views.html.index("あいうえお2 Your new application is ready."))
    //Ok("あいうえお")
    Ok(views.html.main("たいとる")(Html("aaa")))
  }

  val taskForm = Form("label" -> nonEmptyText)

  def tasks = Action {
    Ok(views.html.task(Task.all(), taskForm))
  }

  def newTask = Action { implicit request =>
    taskForm.bindFromRequest.fold(
      errors => BadRequest(views.html.index(Task.all(), errors)),
      label => {
        Task.create(label)
        Redirect(routes.Application.tasks)
      })
  }

  def deleteTask(id: Long) = Action {
    Task.delete(id)
    Redirect(routes.Application.tasks)
  }
}